package br.edu.ifpb.pweb2.estagiotrack.controller;

import br.edu.ifpb.pweb2.estagiotrack.model.Empresa;
import br.edu.ifpb.pweb2.estagiotrack.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @RequestMapping("/form")
    public String getForm(Empresa empresa, Model model) {
        // Para facilitar os testes, iniciamos o projeto com algumas empresas inseridas
        // ao acessar cadastrar empresa e voltar.
        // Quando tivermos um banco, mover esses inserts para um trigger rodando na
        // criação da tabela. Atualmente os registros são reescritos cada vez que a
        // página de cadastro é aberta. Isso vai dar problema quando tivermos
        // integridade referencial entre as entidades.
        empresaRepository.save(new Empresa(
                1,
                "12345678000100", // cnpj
                "58000000", // cep
                "Rua Exemplo A", // logradouro
                "123", // numeroImovel
                "Bairro A", // bairro
                "Cidade A", // cidade
                "PB", // estado
                "(83) 1234-5678", // telefone
                "Atividade A", // atividadePrincipal
                "www.empresaA.com.br", // site
                null, // comprovanteEndereco (pode ser null ou passar um byte[] válido)
                "Empresa A",
                "Responsável Emp A",
                "responsavela@empresa.com",
                "123"));

        empresaRepository.save(new Empresa(
                2,
                "22345678000100", // cnpj
                "58000001", // cep
                "Rua Exemplo B", // logradouro
                "124", // numeroImovel
                "Bairro B", // bairro
                "Cidade B", // cidade
                "PB", // estado
                "(83) 1234-5679", // telefone
                "Atividade B", // atividadePrincipal
                "www.empresaB.com.br", // site
                null, // comprovanteEndereco
                "Empresa B",
                "Responsável Emp B",
                "responsavelb@empresa.com",
                "123"));

        empresaRepository.save(new Empresa(
                3,
                "32345678000100", // cnpj
                "58000002", // cep
                "Rua Exemplo C", // logradouro
                "125", // numeroImovel
                "Bairro C", // bairro
                "Cidade C", // cidade
                "PB", // estado
                "(83) 1234-5680", // telefone
                "Atividade C", // atividadePrincipal
                "www.empresaC.com.br", // site
                null, // comprovanteEndereco
                "Empresa C",
                "Responsável Emp C",
                "responsavelc@empresa.com",
                "123"));

        model.addAttribute("empresa", empresa);
        return "empresas/form";
    }

    @RequestMapping()
    public String getList(Model model) {
        model.addAttribute("empresas", empresaRepository.findAll());
        return "empresas/list";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String cadastroEmpresa(Empresa empresa, Model model, RedirectAttributes attr) {
        if (empresa.getRazaoSocial().isEmpty() || empresa.getEmail().isEmpty() || empresa.getSenha().isEmpty()) {
            model.addAttribute("alert", "Por favor preencha todos os campos corretamente.");
            return "empresas/form";
        } else {
            empresaRepository.save(empresa);
            attr.addFlashAttribute("mensagem", "Empresa cadastrada com sucesso!");
            return "redirect:/empresas";
        }
    }

    // Parte do workaround para vincular oferta a empresa dinamicamente.
    public Empresa buscarPorEmail(String email) {
        Empresa empresa = empresaRepository.findByEmail(email);
        if (empresa != null) {
            return empresa;
        } else {
            return null;
        }
    }

    @RequestMapping("/detalhes/{id}")
    public String getDetalhesEmpresa(@PathVariable("id") Integer id, Model model) {
        Empresa empresa = empresaRepository.findById(id);
        if (empresa != null) {
            model.addAttribute("empresa", empresa);
            return "empresas/detalhes";
        } else {
            model.addAttribute("alert", "Empresa não encontrada.");
            return "redirect:/empresas";
        }
    }

}
