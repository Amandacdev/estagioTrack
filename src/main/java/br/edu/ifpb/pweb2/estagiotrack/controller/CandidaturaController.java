package br.edu.ifpb.pweb2.estagiotrack.controller;

import java.security.Principal;
import br.edu.ifpb.pweb2.estagiotrack.model.Aluno;
import br.edu.ifpb.pweb2.estagiotrack.model.Candidatura;
import br.edu.ifpb.pweb2.estagiotrack.model.Oferta;
import br.edu.ifpb.pweb2.estagiotrack.service.AlunoService;
import br.edu.ifpb.pweb2.estagiotrack.service.CandidaturaService;
import br.edu.ifpb.pweb2.estagiotrack.service.OfertaService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

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
    public String getForm(@RequestParam("ofertaId") Integer ofertaId, Model model) {
        Candidatura candidatura = new Candidatura();

        Oferta ofertaSelecionada = ofertaService.findById(ofertaId).orElse(null);

        candidatura.setOfertaSelecionada(ofertaSelecionada);

        model.addAttribute("candidatura", candidatura);
        model.addAttribute("ofertas", ofertaService.findAll());

        return "candidaturas/form";
    }

    @RequestMapping()
    public String getList(Model model) {
        model.addAttribute("candidaturas", candidaturaService.findAll());
        return "candidaturas/list";
    }

    @PostMapping("/save")
    public String cadastroCandidatura(@ModelAttribute Candidatura candidatura, Model model, RedirectAttributes attr, Principal principal) {
        Aluno aluno = alunoService.findByEmail(candidatura.getEmailCandidato()).orElse(null);
        Oferta oferta = ofertaService.findById(candidatura.getOfertaSelecionada().getId()).orElse(null);

        if (aluno != null && oferta != null) {
            candidatura.setAlunoCandidato(aluno);
            candidatura.setOfertaSelecionada(oferta);
            if (candidatura.getId() == null) {
                Integer maxId = candidaturaService.findMaxId();
                candidatura.setId(maxId + 1);
            }
            candidaturaService.save(candidatura);
            attr.addFlashAttribute("success", "Candidatura realizada com sucesso!");
            return getListCandidaturasUsuario(model, principal);
        } else {
            model.addAttribute("alert", "Email inválido ou oferta não encontrada.");
            return "candidaturas/form";
        }
    }

    @RequestMapping("/paginaUsuario")
    public String getListCandidaturasUsuario(Model model, Principal principal) {
        List<Candidatura> candidaturas = candidaturaService.findAll();

        List<Candidatura> candidaturasUsuario = candidaturas.stream()
                .filter(candidatura -> candidatura.getEmailCandidato().equals(principal.getName()))
                .toList();
        
        Aluno aluno = alunoService.findByEmail(principal.getName()).orElse(null);
        model.addAttribute("candidaturas", candidaturasUsuario);
        model.addAttribute("aluno", aluno);

        return "paginaUsuario/candidaturasEstudante";

    }

    @RequestMapping("/aprovar/{id}")
    public String aprovarCandidatura(@PathVariable("id") Integer id, RedirectAttributes attr) {
        Optional<Candidatura> candidaturaOptional = Optional.ofNullable(candidaturaService.findById(id));

        if (candidaturaOptional.isPresent()) {
            // Redireciona para o formulário de cadastro de estágio com a candidatura
            // selecionada
            return "redirect:/estagios/form/" + id;
        } else {
            attr.addFlashAttribute("alert", "Candidatura não encontrada");
            return "redirect:/candidaturas";
        }
    }

}
