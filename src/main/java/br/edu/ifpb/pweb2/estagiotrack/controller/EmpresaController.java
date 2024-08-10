package br.edu.ifpb.pweb2.estagiotrack.controller;

import br.edu.ifpb.pweb2.estagiotrack.model.Empresa;
import br.edu.ifpb.pweb2.estagiotrack.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @RequestMapping("/form")
    public String getForm(Empresa empresa, Model model){
        model.addAttribute("empresa", empresa);
        return "empresas/form";
    }

    @RequestMapping()
    public String getList(Model model){
        model.addAttribute("empresas", empresaRepository.findAll());
        return "empresas/list";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String cadastroEmpresa(Empresa empresa, Model model, RedirectAttributes attr){
        if(empresa.getRazaoSocial().isEmpty() || empresa.getEmail().isEmpty() || empresa.getSenha().isEmpty()){
            model.addAttribute("alert", "Por favor preencha todos os campos corretamente.");
            return "empresas/form";
        } else {
            empresaRepository.save(empresa);
            //attr.addFlashAttribute("mensagem", "Empresa cadastrada com sucesso!");
            return "redirect:/empresas";
        }
    }
}
