package br.edu.ifpb.pweb2.estagiotrack.controller;

import br.edu.ifpb.pweb2.estagiotrack.model.Aluno;
import br.edu.ifpb.pweb2.estagiotrack.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    @RequestMapping("/form")
    public String getForm(Aluno aluno, Model model){
        model.addAttribute("aluno", aluno);
        return "alunos/form";
    }

    @Autowired
    private AlunoRepository alunoRepository;

    @RequestMapping("/save")
    public String save(Aluno aluno, Model model){
        alunoRepository.save(aluno);
        model.addAttribute("alunos",alunoRepository.findAll());
        return "alunos/list";
    }
}
