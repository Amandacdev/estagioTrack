package br.edu.ifpb.pweb2.estagiotrack.controller;

import br.edu.ifpb.pweb2.estagiotrack.model.Candidatura;
import br.edu.ifpb.pweb2.estagiotrack.model.Estagio;
import br.edu.ifpb.pweb2.estagiotrack.model.Oferta;
import br.edu.ifpb.pweb2.estagiotrack.model.enums.StatusCandidatura;
import br.edu.ifpb.pweb2.estagiotrack.service.CandidaturaService;
import br.edu.ifpb.pweb2.estagiotrack.service.EstagioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/estagios")
public class EstagioController {

    @Autowired
    private CandidaturaService candidaturaService;

    @Autowired
    private EstagioService estagioService;

    @Autowired
    private OfertaController ofertaController;

    @RequestMapping("/form/{candidaturaId}")
    public String getForm(@PathVariable("candidaturaId") Integer candidaturaId, Model model) {
        Optional<Candidatura> candidaturaOptional = Optional.ofNullable(candidaturaService.findById(candidaturaId));

        if (candidaturaOptional.isPresent()) {
            Candidatura candidatura = candidaturaOptional.get();
            Estagio estagio = new Estagio();
            estagio.setAlunoAprovado(candidatura.getAlunoCandidato());
            estagio.setOfertaSelecionada(candidatura.getOfertaSelecionada());
            estagio.setCandidaturaSelecionada(candidatura);

            model.addAttribute("estagio", estagio);
            model.addAttribute("candidatura", candidatura);

            return "estagios/form";
        }

        model.addAttribute("alert", "Candidatura não encontrada");
        return "redirect:/candidaturas";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getList(Model model) {
        java.util.List<Estagio> estagios = estagioService.findAll();
        model.addAttribute("estagios", estagios);
        return "estagios/list";
    }

    @PostMapping("/save")
    public String salvarEstagio(@ModelAttribute Estagio estagio, RedirectAttributes attr, Model model) {
        Oferta oferta = estagio.getOfertaSelecionada();
        if (oferta != null) {
            ofertaController.desativarOferta(oferta.getId(), attr, model);
            Candidatura candidaturaSelecionada = candidaturaService
                    .findById(estagio.getCandidaturaSelecionada().getId());
            candidaturaSelecionada.setStatusCandidatura(StatusCandidatura.APROVADA);

            estagioService.save(estagio);

            attr.addFlashAttribute("success", "Estágio cadastrado com sucesso!");
            return "redirect:/estagios/detalhes/" + estagio.getId();
        }

        attr.addFlashAttribute("error", "Erro ao salvar o estágio.");
        return "redirect:/estagios/form";
    }

    @RequestMapping("/detalhes/{id}")
    public String getDetalhesEstagio(@PathVariable("id") Integer id, Model model) {
        Optional<Estagio> estagioOptional = estagioService.findById(id);
        if (estagioOptional.isPresent()) {
            model.addAttribute("estagio", estagioOptional.get());
            return "estagios/detalhes";
        }
        model.addAttribute("alert", "Estágio não encontrado");
        return "redirect:/estagios";
    }
}
