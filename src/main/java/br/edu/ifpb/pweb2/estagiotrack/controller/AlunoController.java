package br.edu.ifpb.pweb2.estagiotrack.controller;

import br.edu.ifpb.pweb2.estagiotrack.model.Aluno;
import br.edu.ifpb.pweb2.estagiotrack.service.AlunoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;

    @RequestMapping("/form")
    public String getForm(Model model) {

        model.addAttribute("aluno", new Aluno());
        return "alunos/form";
    }

    @RequestMapping()
    public String getList(Model model) {
        model.addAttribute("alunos", alunoService.listAll());
        return "alunos/list";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String cadastroAluno(@RequestParam List<String> competencias, Aluno aluno, Model model,
            RedirectAttributes attr) {
        if (!alunoService.validarAluno(aluno)) {
            model.addAttribute("alert", "Por favor preencha todos os campos corretamente.");
            return "alunos/form";
        } else {
            alunoService.salvarAluno(aluno, competencias);
            attr.addFlashAttribute("success", "Aluno cadastrado com sucesso!");
            return "redirect:/ofertas";
        }
    }

    public Aluno buscarPorEmail(String email) {
        return alunoService.findByEmail(email).orElse(null);
    }
}
