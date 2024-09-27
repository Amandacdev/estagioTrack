package br.edu.ifpb.pweb2.estagiotrack.controller;

import br.edu.ifpb.pweb2.estagiotrack.model.Empresa;
import br.edu.ifpb.pweb2.estagiotrack.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @RequestMapping("/form")
    public String getForm(Empresa empresa, Model model) {
        return "empresas/form";
    }

    @RequestMapping()
    public String getList(Model model) {
        model.addAttribute("empresas", empresaService.findAll());
        return "empresas/list";
    }

    // Método para criar ou editar a empresa
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String salvarEmpresa(
            Empresa empresa,
            Model model,
            RedirectAttributes attr) {
    
        try {
            empresaService.save(empresa);
            attr.addFlashAttribute("success", "Empresa cadastrada/atualizada com sucesso!");
        } catch (IllegalArgumentException e) {
            model.addAttribute("alert", e.getMessage());
            return "empresas/form";
        }

        return "redirect:/empresas/detalhes/" + empresa.getId();
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

    // Método para buscar empresa por email (mantido para compatibilidade)
    public Empresa buscarPorEmail(String email) {
        return empresaService.findByEmail(email)
                .orElse(null);
    }
}
