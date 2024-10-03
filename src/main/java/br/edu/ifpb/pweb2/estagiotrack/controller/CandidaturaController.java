package br.edu.ifpb.pweb2.estagiotrack.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import br.edu.ifpb.pweb2.estagiotrack.model.*;
import br.edu.ifpb.pweb2.estagiotrack.service.EstagioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.estagiotrack.service.AlunoService;
import br.edu.ifpb.pweb2.estagiotrack.service.CandidaturaService;
import br.edu.ifpb.pweb2.estagiotrack.service.OfertaService;

@Controller
@RequestMapping("/candidaturas")
public class CandidaturaController {

    @Autowired
    private CandidaturaService candidaturaService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private OfertaService ofertaService;

    @Autowired
    private EstagioService estagioService;

    @RequestMapping("/form")
    public String getForm(@RequestParam("ofertaId") Integer ofertaId, Model model) {
        Candidatura candidatura = new Candidatura();

        Oferta ofertaSelecionada = ofertaService.findById(ofertaId).orElse(null);

        candidatura.setOfertaSelecionada(ofertaSelecionada);

        model.addAttribute("candidatura", candidatura);
        model.addAttribute("ofertas", ofertaService.findAll());

        return "candidaturas/form";
    }

    @GetMapping
    public String getList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Candidatura> candidaturasPage = candidaturaService.listAll(pageable);

        Paginador paginador = new Paginador(
                candidaturasPage.getNumber(),
                candidaturasPage.getSize(),
                candidaturasPage.getTotalPages()
        );

        model.addAttribute("paginador", paginador);
        model.addAttribute("candidaturas", candidaturasPage.getContent());

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
            return getListCandidaturasUsuario(0, 5,model, principal);
        } else {
            model.addAttribute("alert", "Email inválido ou oferta não encontrada.");
            return "candidaturas/form";
        }
    }

    @RequestMapping("/paginaUsuario")
    public String getListCandidaturasUsuario(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "5") int size,
                                             Model model, Principal principal) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Candidatura> candidaturasPage = candidaturaService.listAll(pageable);

        String email = principal.getName();
        Page<Candidatura> candidaturas = candidaturaService.findByEmailCandidato(email, pageable);

        Paginador paginador = new Paginador(
                candidaturasPage.getNumber(),
                candidaturasPage.getSize(),
                (int) candidaturasPage.getTotalElements()
        );

        Aluno aluno = alunoService.findByEmail(principal.getName()).orElse(null);

        Optional<Estagio> estagioOptional = estagioService.buscarEstagioPorAlunoId(aluno.getId());
        Estagio estagio = estagioOptional.orElse(null);

        model.addAttribute("candidaturas", candidaturas);
        model.addAttribute("aluno", aluno);
        model.addAttribute("estagio", estagio);
        model.addAttribute("paginador", paginador);

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
