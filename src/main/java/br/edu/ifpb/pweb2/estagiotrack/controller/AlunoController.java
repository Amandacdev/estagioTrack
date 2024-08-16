package br.edu.ifpb.pweb2.estagiotrack.controller;

import br.edu.ifpb.pweb2.estagiotrack.model.Aluno;
import br.edu.ifpb.pweb2.estagiotrack.model.Candidatura;
import br.edu.ifpb.pweb2.estagiotrack.model.Oferta;
import br.edu.ifpb.pweb2.estagiotrack.repository.AlunoRepository;
import br.edu.ifpb.pweb2.estagiotrack.repository.CandidaturaRepository;
import br.edu.ifpb.pweb2.estagiotrack.repository.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;
    private CandidaturaRepository candidaturaRepository;
    private OfertaRepository ofertaRepository;

    @RequestMapping("/form")
    public String getForm(Aluno aluno, Model model){
        model.addAttribute("aluno", aluno);
        return "alunos/form";
    }

    @RequestMapping()
    public String getList(Model model){
        model.addAttribute("alunos", alunoRepository.findAll());
        return "alunos/list";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String cadastroAluno(Aluno aluno, Model model, RedirectAttributes attr){
        if(aluno.getNome().isEmpty() || aluno.getEmail().isEmpty() || aluno.getSenha().isEmpty()){
            model.addAttribute("alert", "Por favor preencha todos os campos corretamente.");
            return "alunos/form";
        } else {
            alunoRepository.save(aluno);
            //attr.addFlashAttribute("success", "Aluno cadastrado com sucesso!");
            return "redirect:/alunos";
        }
    }

    @RequestMapping("/candidatar")
    public String candidatarOferta(Integer ofertaId, Integer alunoId, RedirectAttributes attr) {
        Aluno aluno = alunoRepository.findById(alunoId);
        Oferta oferta = ofertaRepository.findById(ofertaId);

        if (aluno == null || oferta == null) {
            attr.addFlashAttribute("alert", "Erro ao candidatar-se à oferta.");
            return "redirect:/ofertas";
        }

        Candidatura candidatura = new Candidatura(aluno, oferta);
        candidaturaRepository.save(candidatura);
        attr.addFlashAttribute("success", "Candidatura realizada com sucesso!");
        return "redirect:/ofertas";
    }
}
