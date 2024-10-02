package br.edu.ifpb.pweb2.estagiotrack.controller;

import java.security.Principal;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.estagiotrack.model.Empresa;
import br.edu.ifpb.pweb2.estagiotrack.model.Paginador;
import br.edu.ifpb.pweb2.estagiotrack.service.EmpresaService;

@Controller
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private JdbcUserDetailsManager jdbcUserDetailsManager;

    @RequestMapping("/form")
    public String getForm(Empresa empresa, Model model) {
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
                (int) empresasPage.getTotalElements());

        model.addAttribute("paginador", paginador);
        model.addAttribute("empresas", empresasPage.getContent());

        return "empresas/list";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String cadastroEmpresa(Empresa empresa, Model model, RedirectAttributes attr, Principal principal) {
        System.out.println(empresa);
        Empresa empresaExistente = null;

        if (empresa.getId() != null) {
            empresaExistente = empresaService.findById(empresa.getId()).orElse(null);
        }

        // Verificar se o email ou CNPJ já existem, excluindo a própria empresa (caso
        // seja edição)
        if ((empresaService.existsByEmail(empresa.getEmail())
                && (empresaExistente == null || !empresaExistente.getEmail().equals(empresa.getEmail()))) ||
                (empresaService.existsByCnpj(empresa.getCnpj())
                        && (empresaExistente == null || !empresaExistente.getCnpj().equals(empresa.getCnpj())))) {
            model.addAttribute("alert", "Email ou CNPJ já cadastrado.");
            return "empresas/form";
        }

        // Se é uma nova empresa, definimos o ID
        if (empresa.getId() == null) {
            Integer maxId = empresaService.findMaxId();
            empresa.setId(maxId + 1);
        }

        empresaService.save(empresa);

        // Lógica de usuário no caso de criação de uma nova empresa
        if (empresaExistente == null) {
            UserDetails novoUsuario = User.withUsername(empresa.getEmail()).password(empresa.getSenha())
                    .roles("EMPRESA").build();
            if (!jdbcUserDetailsManager.userExists(empresa.getEmail())) {
                jdbcUserDetailsManager.createUser(novoUsuario);
            }
            attr.addFlashAttribute("success", "Empresa cadastrada com sucesso. Faça login para continuar.");
            return "redirect:/auth";
        } else {
            attr.addFlashAttribute("success", "Empresa atualizada com sucesso.");
            return "redirect:/empresas/detalhes/" + empresa.getId();
        }

    }

    // Método para deletar a empresa
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deletarEmpresa(@RequestParam Integer empresaId, RedirectAttributes attr) {
        empresaService.deleteById(empresaId);
        attr.addFlashAttribute("mensagem", "Empresa excluída com sucesso!");
        return "redirect:/empresas";
    }

    // Detalhes da empresa
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

    // Exibir o formulário de edição de uma empresa
    @RequestMapping(value = "/editar/{id}", method = RequestMethod.GET)
    public String editarEmpresa(@PathVariable("id") Integer id, Model model) {
        Optional<Empresa> empresa = empresaService.findById(id);
        if (empresa.isPresent()) {
            model.addAttribute("empresa", empresa.get());
            return "empresas/form"; // Reutilizando o formulário de cadastro para edição
        } else {
            model.addAttribute("alert", "Empresa não encontrada.");
            return "redirect:/empresas";
        }
    }

    // Adicionando o método de bloquear empresa
    @RequestMapping(value = "/bloquear/{id}", method = RequestMethod.POST)
    public String bloquearEmpresa(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        Optional<Empresa> empresaOpt = empresaService.findById(id);
        if (empresaOpt.isPresent()) {
            Empresa empresa = empresaOpt.get();
            empresa.setBloqueada(true);
            empresaService.save(empresa); // Atualiza a empresa
            redirectAttributes.addFlashAttribute("success", "Empresa bloqueada com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("alert", "Empresa não encontrada.");
        }
        return "redirect:/empresas";
    }

    @RequestMapping(value = "/desbloquear/{id}", method = RequestMethod.POST)
    public String desbloquearEmpresa(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        Optional<Empresa> empresaOpt = empresaService.findById(id);
        if (empresaOpt.isPresent()) {
            Empresa empresa = empresaOpt.get();
            empresa.setBloqueada(false); // Desbloqueia a empresa
            empresaService.save(empresa); // Atualiza a empresa
            redirectAttributes.addFlashAttribute("success", "Empresa desbloqueada com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("alert", "Empresa não encontrada.");
        }
        return "redirect:/empresas";
    }

    // Método para buscar empresa por email (mantido para compatibilidade)
    public Empresa buscarPorEmail(String email) {
        return empresaService.findByEmail(email)
                .orElse(null);
    }
}
