package br.edu.ifpb.pweb2.estagiotrack.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import br.edu.ifpb.pweb2.estagiotrack.model.Paginador;
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
        // Verifica se o usuário está autenticado
        if (principal == null || principal.getName() == null) {
            model.addAttribute("message", "Usuário não autenticado.");
            return "error"; // Redireciona para a página de erro personalizada
        }

        // Recupera a empresa pelo email do usuário logado
        Empresa empresa = empresaController.buscarPorEmail(principal.getName());

        if (empresa == null) {
            model.addAttribute("message", "Empresa não encontrada. Verifique suas credenciais.");
            return "error";
        }

        // Verifica se a empresa está bloqueada
        if (empresa.isBloqueada()) {
            model.addAttribute("message", "Sua empresa está bloqueada e não pode cadastrar ofertas.");
            return "error";
        }

        // Passa a empresa e a oferta para o modelo
        List<CompetenciaTemplate> competenciasTemplate = competenciasTemplateService.findAll();
        model.addAttribute("competenciasTemplate", competenciasTemplate);
        model.addAttribute("empresa", empresa);
        model.addAttribute("oferta", oferta);

        return "ofertas/form";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getList(@RequestParam(value = "competencias", required = false) List<String> competencias,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Oferta> ofertasPage;
        if (competencias == null || competencias.isEmpty()) {
            ofertasPage = ofertaService.listAll(pageable);
        } else {
            ofertasPage = ofertaService.findByCompetencias(String.valueOf(competencias), pageable);
        }

        Paginador paginador = new Paginador(
                ofertasPage.getNumber(),
                ofertasPage.getSize(),
                (int) ofertasPage.getTotalElements());

        List<CompetenciaTemplate> competenciasTemplate = competenciasTemplateService.findAll();
        model.addAttribute("competenciasTemplate", competenciasTemplate);
        model.addAttribute("ofertas", ofertasPage);
        model.addAttribute("paginador", paginador);
        return "ofertas/list";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String cadastroOferta(@RequestParam List<String> competencias, Oferta oferta, Model model,
            RedirectAttributes attr, Principal principal) {

        // Verifica se os campos obrigatórios estão preenchidos
        if (oferta.getTituloCargo().isEmpty() || oferta.getCargaHoraria().isEmpty()) {
            model.addAttribute("alert", "Por favor, preencha todos os campos corretamente.");
            return "ofertas/form";
        } else {
            // Busca a empresa pelo email do usuário autenticado
            String emailOfertante = principal.getName();
            Empresa empresa = empresaController.buscarPorEmail(emailOfertante);

            if (empresa != null) {
                // Verifica se a empresa está bloqueada
                if (empresa.isBloqueada()) {
                    model.addAttribute("alert",
                            "Sua empresa está bloqueada e não pode cadastrar ofertas, entre em contato com o suporte.");
                    return "ofertas/form";
                }

                // Se for uma nova oferta, define um novo ID
                if (oferta.getId() == null) {
                    Integer maxId = ofertaService.findMaxId();
                    oferta.setId(maxId + 1);
                }

                // Define a empresa ofertante e salva a oferta
                oferta.setEmailOfertante(principal.getName());
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
    public String getListOfertasUsuario(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model, Principal principal) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Oferta> ofertasPage = ofertaService.findByEmailOfertante(principal.getName(), pageable);
        Empresa empresa = empresaController.buscarPorEmail(principal.getName());

        Paginador paginador = new Paginador(
                ofertasPage.getNumber(),
                ofertasPage.getSize(),
                (int) ofertasPage.getTotalElements());

        System.out.println(paginador);

        model.addAttribute("empresa", empresa);
        model.addAttribute("ofertas", ofertasPage);
        model.addAttribute("paginador", paginador);

        return "paginaUsuario/ofertasEmpresa";

    }

    @RequestMapping("/listOfertasAbertas")
    public String getOfertasAbertas(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Oferta> ofertasAbertas = ofertaService.listarOfertasAbertas(pageable);

        List<CompetenciaTemplate> competenciasTemplate = competenciasTemplateService.findAll();

        Paginador paginador = new Paginador(
                ofertasAbertas.getNumber(),
                ofertasAbertas.getSize(),
                (int) ofertasAbertas.getTotalElements());

        model.addAttribute("competenciasTemplate", competenciasTemplate);
        model.addAttribute("ofertas", ofertasAbertas);
        model.addAttribute("paginador", paginador);

        return "ofertas/listOfertasAbertas";
    }
}
