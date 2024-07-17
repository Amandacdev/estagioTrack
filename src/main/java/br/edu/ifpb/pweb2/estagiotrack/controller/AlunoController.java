package br.edu.ifpb.pweb2.estagiotrack.controller;

import br.edu.ifpb.pweb2.estagiotrack.model.Aluno;
import br.edu.ifpb.pweb2.estagiotrack.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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

    //Falta ajustar o redirect
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String cadastroAluno(Aluno aluno, Model model, RedirectAttributes attr){
        if(aluno.getNome().isEmpty() || aluno.getEmail().isEmpty() || aluno.getSenha().isEmpty()){
            model.addAttribute("alert", "Por favor preencha os campos.");
            return "alunos/form";
        } else {
            alunoRepository.save(aluno);
            model.addAttribute("alunos",alunoRepository.findAll());
            return "alunos/list";
            //attr.addFlashAttribute("mensagem", "Aluno cadastrado com sucesso!");
            //return "redirect:/alunos/list";
        }

    }
}
