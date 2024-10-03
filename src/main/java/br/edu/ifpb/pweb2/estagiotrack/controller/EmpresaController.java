package br.edu.ifpb.pweb2.estagiotrack.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.estagiotrack.model.Empresa;
import br.edu.ifpb.pweb2.estagiotrack.model.Paginador;
import br.edu.ifpb.pweb2.estagiotrack.service.EmpresaService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private JdbcUserDetailsManager jdbcUserDetailsManager;

    @Autowired
    private FileController fileController;

    @RequestMapping("/form")
    public String getForm(Empresa empresa, Model model) {
        model.addAttribute("empresa", empresa);
        return "empresas/form";
    }

    @GetMapping
    public String getList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Empresa> empresasPage = empresaService.listAll(pageable);

        Paginador paginador = new Paginador(
                empresasPage.getNumber(),
                empresasPage.getSize(),
                (int) empresasPage.getTotalElements()
        );

        model.addAttribute("paginador", paginador);
        model.addAttribute("empresas", empresasPage.getContent());

        return "empresas/list";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String cadastroEmpresa(
            @Valid Empresa empresa,
            BindingResult bindingResult,
            @RequestParam("comprovante") MultipartFile comprovante,
            Model model,
            RedirectAttributes attr) {

        // Debug: Imprime informações do MultipartFile
        System.out.println("Nome do arquivo: " + comprovante.getOriginalFilename());
        System.out.println("Tamanho do arquivo: " + comprovante.getSize());

        // Verifica se o comprovante foi enviado
        if (comprovante.isEmpty()) {
            model.addAttribute("alert", "Por favor, envie um comprovante de endereço.");
            return "empresas/form";
        }

        // Verifica se há erros de validação no formulário
        if (bindingResult.hasErrors()) {
            System.out.println("Erros de validação: " + bindingResult.getAllErrors());
            model.addAttribute("alert", "Por favor, preencha todos os campos corretamente.");
            return "empresas/form";
        }

        // Verifica se o email ou CNPJ já estão cadastrados
        if (empresaService.existsByEmail(empresa.getEmail()) || empresaService.existsByCnpj(empresa.getCnpj())) {
            model.addAttribute("alert", "Email ou CNPJ já cadastrado.");
            return "empresas/form";
        }

        try {
            // Salva a empresa no banco
            empresaService.save(empresa);
            System.out.println("Empresa salva: " + empresa);

            // Salva o comprovante no banco usando o serviço de arquivo
            fileController.uploadComprovante(empresa.getId(), comprovante);
            System.out.println("Comprovante salvo: " + comprovante);

            // Cria o usuário no sistema de autenticação (caso ainda não exista)
            UserDetails novoUsuario = User.withUsername(empresa.getEmail())
                    .password(empresa.getSenha())
                    .roles("EMPRESA")
                    .build();

            if (!jdbcUserDetailsManager.userExists(empresa.getEmail())) {
                jdbcUserDetailsManager.createUser(novoUsuario); // Salva o novo usuário no banco de dados
                System.out.println("Novo usuário criado: " + novoUsuario);
            }

            // Atribui a mensagem de sucesso ao RedirectAttributes
            attr.addFlashAttribute("success", "Empresa cadastrada com sucesso. Faça login para continuar.");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar empresa: " + e.getMessage());
            model.addAttribute("alert", "Erro ao cadastrar a empresa. Tente novamente.");
            return "empresas/form";
        }

        return "redirect:/auth";  // Redireciona para a página de login
    }



    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deletaEmpresa(@RequestParam Integer empresaId, RedirectAttributes attr) {
        if (empresaService.existsById(empresaId)) {
            empresaService.deleteById(empresaId);
            attr.addFlashAttribute("mensagem", "Empresa excluída com sucesso!");
        } else {
            attr.addFlashAttribute("alert", "Empresa não encontrada.");
        }
        return "redirect:/empresas";
    }

    public Empresa buscarPorEmail(String email) {
        return empresaService.findByEmail(email)
                .orElse(null);
    }

    @RequestMapping("/detalhes/{id}")
    public String getDetalhesEmpresa(@PathVariable("id") Integer id, Model model) {
        Optional<Empresa> empresa = empresaService.findById(id);
        if (empresa.isPresent()) {
            model.addAttribute("empresa", empresa.get());
            return "empresas/detalhes";
        } else {
            model.addAttribute("alert", "Empresa não encontrada.");
            return "redirect:/empresas";
        }
    }

}
