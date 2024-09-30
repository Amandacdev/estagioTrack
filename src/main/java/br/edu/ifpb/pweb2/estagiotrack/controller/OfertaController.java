package br.edu.ifpb.pweb2.estagiotrack.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.estagiotrack.model.Candidatura;
import br.edu.ifpb.pweb2.estagiotrack.model.CompetenciaTemplate;
import br.edu.ifpb.pweb2.estagiotrack.model.Empresa;
import br.edu.ifpb.pweb2.estagiotrack.model.Oferta;
import br.edu.ifpb.pweb2.estagiotrack.model.enums.StatusCandidatura;
import br.edu.ifpb.pweb2.estagiotrack.model.enums.StatusOferta;
import br.edu.ifpb.pweb2.estagiotrack.repository.CandidaturaRepository;
import br.edu.ifpb.pweb2.estagiotrack.repository.OfertaRepository;
import br.edu.ifpb.pweb2.estagiotrack.service.CompetenciasTemplateService;
import br.edu.ifpb.pweb2.estagiotrack.service.OfertaService;

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

    @Autowired
    private CompetenciasTemplateService competenciasTemplateService;

    @RequestMapping("/form")
    public String getForm(Oferta oferta, Model model, Principal principal) {
        System.out.println(principal);
        System.out.println(principal.getName());
        List<CompetenciaTemplate> competenciasTemplate = competenciasTemplateService.findAll();
        model.addAttribute("competenciasTemplate", competenciasTemplate);
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
        List<CompetenciaTemplate> competenciasTemplate = competenciasTemplateService.findAll();
        model.addAttribute("competenciasTemplate", competenciasTemplate);
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
                if (oferta.getId() == null) {
                    Integer maxId = ofertaService.findMaxId();
                    oferta.setId(maxId + 1);
                }
                oferta.setOfertante(empresa);
                ofertaRepository.save(oferta);
                attr.addFlashAttribute("success", "Oferta de estágio cadastrada com sucesso!");
                return "redirect:/ofertas/detalhes/" + oferta.getId();
            } else {
                model.addAttribute("alert",
                        "Email inválido. O email deve corresponder ao informado no cadastro da empresa.");
                return "ofertas/form";
            }
        }
    }

    @RequestMapping(value = "/desativar", method = RequestMethod.POST)
    public String desativarOferta(Integer ofertaId, RedirectAttributes attr, Model model) {
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
            oferta.setStatusOferta(StatusOferta.INTERROMPIDA);
            ofertaRepository.save(oferta);

            attr.addFlashAttribute("success", "Oferta de estágio desativada com sucesso!");
            return getDetalhesOferta(oferta.getId(), model);
        } else {
            attr.addFlashAttribute("alert", "Oferta de estágio não encontrada.");
        }
        return "redirect:/ofertas";
    }

    @RequestMapping(value = "/encerrar", method = RequestMethod.POST)
    public String encerrarOferta(Integer ofertaId, RedirectAttributes attr, Model model) {
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
            oferta.setStatusOferta(StatusOferta.FINALIZADA);
            ofertaRepository.save(oferta);

            attr.addFlashAttribute("success", "Oferta de estágio encerrada com sucesso!");
            return getDetalhesOferta(oferta.getId(), model);
        } else {
            attr.addFlashAttribute("alert", "Oferta de estágio não encontrada.");
        }
        return "redirect:/ofertas";
    }

    @RequestMapping(value = "/reativar", method = RequestMethod.POST)
    public String reativarOferta(Integer ofertaId, RedirectAttributes attr, Model model) {
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
            return getDetalhesOferta(oferta.getId(), model);
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
            List<Candidatura> candidaturas = candidaturaRepository.findByOfertaSelecionada(oferta.get());
            model.addAttribute("oferta", oferta.get());
            model.addAttribute("candidaturas", candidaturas);
            return "ofertas/detalhes";
        } else {
            model.addAttribute("alert", "Oferta não encontrada");
            return "redirect:/ofertas";
        }
    }

    // Esse método recebe um objeto empresa, obtem as ofertas desse usuário
    // fornecido e direciona à página de visualização dessas ofertas
    @RequestMapping("/paginaUsuario")
    public String getListOfertasUsuario(Model model, Principal principal) {
        List<Oferta> ofertas = ofertaService.findAll();

        List<Oferta> ofertasUsuario = ofertas.stream()
                .filter(oferta -> oferta.getEmailOfertante().equals(principal.getName()))
                .toList();

        Empresa empresa = empresaController.buscarPorEmail(principal.getName());
        model.addAttribute("ofertas", ofertasUsuario);
        model.addAttribute("empresa", empresa);

        return "paginaUsuario/ofertasEmpresa";

    }

    @RequestMapping("/listOfertasAbertas")
    public String getOfertasAbertas(Model model) {
        List<Oferta> ofertasAbertas = ofertaService.listarOfertasAbertas();
        List<CompetenciaTemplate> competenciasTemplate = competenciasTemplateService.findAll();
        model.addAttribute("competenciasTemplate", competenciasTemplate);
        model.addAttribute("ofertas", ofertasAbertas);
        return "ofertas/listOfertasAbertas";
    }
}
