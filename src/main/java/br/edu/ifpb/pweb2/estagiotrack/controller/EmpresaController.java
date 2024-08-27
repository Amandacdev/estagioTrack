package br.edu.ifpb.pweb2.estagiotrack.controller;

import br.edu.ifpb.pweb2.estagiotrack.model.Empresa;
import br.edu.ifpb.pweb2.estagiotrack.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String cadastroEmpresa(@Valid Empresa empresa,
                                  BindingResult bindingResult,
                                  Model model,
                                  RedirectAttributes attr) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("alert", "Por favor, preencha todos os campos corretamente.");
            return "empresas/form";
        }

        if (empresaService.existsByEmail(empresa.getEmail()) || empresaService.existsByCnpj(empresa.getCnpj())) {
            model.addAttribute("alert", "Email ou CNPJ já cadastrado.");
            return "empresas/form";
        }

        empresaService.save(empresa);
        attr.addFlashAttribute("success", "Empresa cadastrada com sucesso!");
        return "redirect:/empresas";
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
