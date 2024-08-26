package br.edu.ifpb.pweb2.estagiotrack.controller;

import br.edu.ifpb.pweb2.estagiotrack.model.Aluno;
import br.edu.ifpb.pweb2.estagiotrack.model.Candidatura;
import br.edu.ifpb.pweb2.estagiotrack.model.Oferta;
import br.edu.ifpb.pweb2.estagiotrack.service.AlunoService;
import br.edu.ifpb.pweb2.estagiotrack.service.CandidaturaService;
import br.edu.ifpb.pweb2.estagiotrack.service.OfertaService;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/candidaturas")
public class CandidaturaController {

    @Autowired
    private CandidaturaService candidaturaService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private OfertaService ofertaService;



    @RequestMapping("/form")
    public String getForm(Model model) {
        model.addAttribute("candidatura", new Candidatura());
        model.addAttribute("ofertas", ofertaService.findAll());
        return "candidaturas/form";
    }

    @RequestMapping()
    public String getList(Model model) {
        model.addAttribute("candidaturas", candidaturaService.findAll());
        return "candidaturas/list";
    }

    @PostMapping("/save")
    public String cadastroCandidatura(@ModelAttribute Candidatura candidatura, Model model, RedirectAttributes attr) {
        Aluno aluno = alunoService.findByEmail(candidatura.getEmailCandidato()).orElse(null);
        Oferta oferta = ofertaService.findById(candidatura.getOfertaSelecionada().getId()).orElse(null);

        if (aluno != null && oferta != null) {
            candidatura.setAlunoCandidato(aluno);
            candidatura.setOfertaSelecionada(oferta);
            candidaturaService.save(candidatura);
            attr.addFlashAttribute("success", "Candidatura realizada com sucesso!");
            return "redirect:/candidaturas";
        } else {
            model.addAttribute("alert", "Email inválido ou oferta não encontrada.");
            return "candidaturas/form";
        }
    }

    @RequestMapping("/paginaUsuario")
    public String getListCandidaturasUsuario(Model model, Aluno aluno) {
        List<Candidatura> candidaturasUsuario =candidaturaService.findAll();

        List<Candidatura> candidaturasFiltradas = candidaturasUsuario.stream()
                .filter(candidatura -> candidatura.getEmailCandidato().equals(aluno.getEmail()))
                .toList();

        // Adicionando as candidaturas filtradas ao modelo
        model.addAttribute("candidaturas", candidaturasFiltradas);

        return "paginaUsuario/candidaturasEstudante";
    }

}
