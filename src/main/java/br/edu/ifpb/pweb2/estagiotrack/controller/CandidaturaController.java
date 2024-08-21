package br.edu.ifpb.pweb2.estagiotrack.controller;

import br.edu.ifpb.pweb2.estagiotrack.model.Aluno;
import br.edu.ifpb.pweb2.estagiotrack.model.Candidatura;
import br.edu.ifpb.pweb2.estagiotrack.model.Oferta;
import br.edu.ifpb.pweb2.estagiotrack.repository.CandidaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/candidaturas")
public class CandidaturaController {

    @Autowired
    private CandidaturaRepository candidaturaRepository;

    @Autowired
    private AlunoController alunoController;

    @Autowired
    OfertaController ofertaController;

    @RequestMapping("/form")
    public String getForm(Candidatura candidatura, Model model) {
        model.addAttribute(("candidatura"), candidatura);
        model.addAttribute("ofertas", ofertaController.ofertaRepository.findAll());
        return "candidaturas/form";
    }

    @RequestMapping()
    public String getList(Model model) {
        model.addAttribute("candidaturas", candidaturaRepository.findAll());
        return "candidaturas/list";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String cadastroCandidatura(Candidatura candidatura, Model model, RedirectAttributes attr) {
        Aluno aluno = alunoController.buscarPorEmail(candidatura.emailCandidato);
        Oferta oferta = ofertaController.buscarPorId(candidatura.getOfertaSelecionada().getId());

        if (aluno != null && oferta != null) {
            candidatura.setAlunoCandidato(aluno);
            candidatura.setOfertaSelecionada(oferta);
            candidaturaRepository.save(candidatura);
            return "redirect:/candidaturas";
        } else {
            model.addAttribute("alert", "Email inválido. O email deve corresponder ao informado no cadastro do aluno.");
            return "candidaturas/form";
        }
    }
}
