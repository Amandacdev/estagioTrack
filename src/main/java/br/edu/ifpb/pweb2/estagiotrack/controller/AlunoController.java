package br.edu.ifpb.pweb2.estagiotrack.controller;

import br.edu.ifpb.pweb2.estagiotrack.model.Aluno;
import br.edu.ifpb.pweb2.estagiotrack.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Optional;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/alunos")
@RequiredArgsConstructor
@Validated
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
    public String cadastroAluno(@RequestParam List<String> competencias, @Valid Aluno aluno, BindingResult bindingResult, RedirectAttributes attr) {
        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("alunos/form");

            List<String> msg = new ArrayList<>();

            for (ObjectError error : bindingResult.getAllErrors()) {
                msg.add(error.getDefaultMessage());
            }

            mav.addObject("msg", msg);
            return "alunos/form";
        } else {
            alunoService.salvarAluno(aluno, competencias);
            return "redirect:/ofertas";
        }
    }

    public Aluno buscarPorEmail(String email) {
        return alunoService.findByEmail(email).orElse(null);
    }

    @RequestMapping("/detalhes/{id}")
    public String getDetalhesAluno(@PathVariable("id") Integer id, Model model) {
        Optional<Aluno> aluno = alunoService.findById(id);
        if (aluno.isPresent()) {
            model.addAttribute("aluno", aluno.get());
            return "alunos/detalhes";
        } else {
            model.addAttribute("alert", "Estudante não encontrado");
            return "redirect:/alunos";
        }
    }
}
