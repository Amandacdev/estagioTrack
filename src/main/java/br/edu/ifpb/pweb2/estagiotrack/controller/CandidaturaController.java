package br.edu.ifpb.pweb2.estagiotrack.controller;

import br.edu.ifpb.pweb2.estagiotrack.model.*;
import br.edu.ifpb.pweb2.estagiotrack.service.AlunoService;
import br.edu.ifpb.pweb2.estagiotrack.service.CandidaturaService;
import br.edu.ifpb.pweb2.estagiotrack.service.OfertaService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping
    public String getList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Candidatura> candidaturasPage = candidaturaService.listAll(pageable);

        if (candidaturasPage == null || candidaturasPage.isEmpty()) {
            Paginador paginador = new Paginador(0, size, 0);
            model.addAttribute("paginador", paginador);
            model.addAttribute("candidaturas", List.of());
            model.addAttribute("error", "Nenhuma candidatura encontrada.");
        } else {
            Paginador paginador = new Paginador(
                    candidaturasPage.getNumber(),
                    candidaturasPage.getSize(),
                    candidaturasPage.getTotalPages()
            );
            model.addAttribute("paginador", paginador);
            model.addAttribute("candidaturas", candidaturasPage.getContent());
        }

        return "candidaturas/list";
    }

    @PostMapping("/save")
    public String cadastroCandidatura(@ModelAttribute Candidatura candidatura, Model model, RedirectAttributes attr) {
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
            return getListCandidaturasUsuario(model, aluno);
        } else {
            model.addAttribute("alert", "Email inválido ou oferta não encontrada.");
            return "candidaturas/form";
        }
    }

    @RequestMapping("/paginaUsuario")
    public String getListCandidaturasUsuario(Model model, Aluno aluno) {
        List<Candidatura> candidaturas = candidaturaService.findAll();

        List<Candidatura> candidaturasUsuario = candidaturas.stream()
                .filter(candidatura -> candidatura.getEmailCandidato().equals(aluno.getEmail()))
                .toList();

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
