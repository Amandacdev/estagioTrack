package br.edu.ifpb.pweb2.estagiotrack.controller;

import br.edu.ifpb.pweb2.estagiotrack.model.Candidatura;
import br.edu.ifpb.pweb2.estagiotrack.model.Empresa;
import br.edu.ifpb.pweb2.estagiotrack.model.Oferta;
import br.edu.ifpb.pweb2.estagiotrack.model.enums.StatusCandidatura;
import br.edu.ifpb.pweb2.estagiotrack.model.enums.StatusOferta;
import br.edu.ifpb.pweb2.estagiotrack.repository.CandidaturaRepository;
import br.edu.ifpb.pweb2.estagiotrack.repository.OfertaRepository;
import br.edu.ifpb.pweb2.estagiotrack.service.OfertaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ofertas")
public class OfertaController {

    @Autowired
    public OfertaRepository ofertaRepository;

    @Autowired
    private CandidaturaRepository candidaturaRepository;

    @Autowired
    private OfertaService ofertaService;

    @Autowired
    private EmpresaController empresaController;

    @RequestMapping("/form")
    public String getForm(Oferta oferta, Model model) {
        return "ofertas/form";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getList(@RequestParam(value = "competencias", required = false) List<String> competencias,
            Model model) {
        List<Oferta> ofertas;
        if (competencias == null || competencias.isEmpty()) {
            ofertas = ofertaRepository.findAll();
        } else {
            ofertas = ofertaRepository.findByCompetencias(competencias);
        }
        model.addAttribute("ofertas", ofertas);
        return "ofertas/list";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String cadastroOferta(@RequestParam List<String> competencias, Oferta oferta, Model model,
            RedirectAttributes attr) {
        if (oferta.getTituloCargo().isEmpty() || oferta.getEmailOfertante().isEmpty()
                || oferta.getCargaHoraria().isEmpty()) {
            model.addAttribute("alert", "Por favor, preencha todos os campos corretamente.");
            return "ofertas/form";
        } else {
            Empresa empresa = empresaController.buscarPorEmail(oferta.getEmailOfertante());
            if (empresa != null) {
                oferta.setOfertante(empresa);
                ofertaRepository.save(oferta);
                return "redirect:/ofertas";
            } else {
                model.addAttribute("alert",
                        "Email inválido. O email deve corresponder ao informado no cadastro da empresa.");
                return "ofertas/form";
            }
        }
    }

    @RequestMapping(value = "/desativar", method = RequestMethod.POST)
    public String desativarOferta(Integer ofertaId, RedirectAttributes attr) {
        Optional<Oferta> ofertaOptional = ofertaRepository.findById(ofertaId);
        if (ofertaOptional.isPresent()) {
            Oferta oferta = ofertaOptional.get();
            List<Candidatura> candidaturas = candidaturaRepository.findByOfertaSelecionada(oferta);
            if (!candidaturas.isEmpty()) {
                for (Candidatura candidatura : candidaturas) {
                    candidatura.setStatusCandidatura(StatusCandidatura.REJEITADA);
                    candidaturaRepository.save(candidatura);
                }
            }
            oferta.encerrar();
            ofertaRepository.save(oferta);

            attr.addFlashAttribute("mensagem", "Oferta de estágio desativada com sucesso!");
        } else {
            attr.addFlashAttribute("alert", "Oferta de estágio não encontrada.");
        }
        return "redirect:/ofertas";
    }

    @RequestMapping(value = "/reativar", method = RequestMethod.POST)
    public String reativarOferta(Integer ofertaId, RedirectAttributes attr) {
        Optional<Oferta> ofertaOptional = ofertaRepository.findById(ofertaId);
        if (ofertaOptional.isPresent()) {
            Oferta oferta = ofertaOptional.get();
            List<Candidatura> candidaturas = candidaturaRepository.findByOfertaSelecionada(oferta);
            if (!candidaturas.isEmpty()) {
                for (Candidatura candidatura : candidaturas) {
                    candidatura.setStatusCandidatura(StatusCandidatura.PENDENTE);
                    candidaturaRepository.save(candidatura);
                }
            }
            oferta.setStatusOferta(StatusOferta.ABERTA);
            ofertaRepository.save(oferta);
            attr.addFlashAttribute("mensagem", "Oferta de estágio reativada com sucesso!");
        } else {
            attr.addFlashAttribute("alert", "Oferta de estágio não encontrada.");
        }
        return "redirect:/ofertas";
    }

    public Oferta buscarPorId(Integer id) {
        Optional<Oferta> oferta = ofertaRepository.findById(id);
        return oferta.orElse(null);
    }

    @RequestMapping("/detalhes/{id}")
    public String getDetalhesOferta(@PathVariable("id") Integer id, Model model) {
        Optional<Oferta> oferta = ofertaService.findById(id);
        if (oferta.isPresent()) {
            model.addAttribute("oferta", oferta.get());
            return "ofertas/detalhes";
        } else {
            model.addAttribute("alert", "Oferta não encontrada");
            return "redirect:/ofertas";
        }
    }
}
