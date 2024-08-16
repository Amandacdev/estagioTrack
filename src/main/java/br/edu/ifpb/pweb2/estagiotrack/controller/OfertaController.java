package br.edu.ifpb.pweb2.estagiotrack.controller;

import br.edu.ifpb.pweb2.estagiotrack.model.Empresa;
import br.edu.ifpb.pweb2.estagiotrack.model.Oferta;
import br.edu.ifpb.pweb2.estagiotrack.repository.OfertaRepository;
import br.edu.ifpb.pweb2.estagiotrack.utils.Filtro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ofertas")
public class OfertaController {

    @Autowired
    private OfertaRepository ofertaRepository;
    private EmpresaController empresaController;

    @RequestMapping("/filter")
    public String getFilterForm(Model model) {
        model.addAttribute("filtro", new Filtro());
        return "ofertas/filter";
    }

    @RequestMapping(value = "/filtrar", method = RequestMethod.POST)
    public String filtrarOfertas(Filtro filtro, Model model) {
        List<Oferta> ofertasFiltradas = ofertaRepository.findAll().stream()
                .filter(oferta -> oferta.getTituloCargo().contains(filtro.getTituloCargo()))
                .filter(oferta -> oferta.getTurno().equals(filtro.getTurno()) || filtro.getTurno().isEmpty())
                .filter(oferta -> filtro.getRequisitos().isEmpty() || oferta.getTituloCargo().contains(filtro.getRequisitos()))
                .collect(Collectors.toList());
        model.addAttribute("ofertas", ofertasFiltradas);
        return "ofertas/list";
    }

    @RequestMapping("/form")
    public String getForm(Oferta oferta, Model model) {
        model.addAttribute("oferta", oferta);
        return "ofertas/form";
    }

    @RequestMapping()
    public String getList(Model model) {
        model.addAttribute("ofertas", ofertaRepository.findAll());
        return "ofertas/list";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String cadastroOferta(Oferta oferta, Model model, RedirectAttributes attr) {
        if (oferta.getTituloCargo().isEmpty() || oferta.getEmailOfertante().isEmpty() || oferta.getTurno().isEmpty()) {
            model.addAttribute("alert", "Por favor, preencha todos os campos corretamente.");
            return "ofertas/form";
        } else {
            Empresa empresa = empresaController.buscarPorEmail(oferta.emailOfertante); //Parte do workaround para vincular oferta a empresa dinamicamente.
            if (empresa != null) {
                oferta.setOfertante(empresa);
                ofertaRepository.save(oferta);
                return "redirect:/ofertas";
            } else {
                model.addAttribute("alert", "Email inválido. O email deve corresponder ao informado no cadastro da empresa.");
                return "ofertas/form";
            }
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deletaOferta(Integer ofertaId, RedirectAttributes attr) {
        Oferta oferta = ofertaRepository.findById(ofertaId);
        if (oferta != null) {
            ofertaRepository.delete(ofertaId);
            attr.addFlashAttribute("mensagem", "Oferta de estágio cancelada com sucesso!");
        } else {
            attr.addFlashAttribute("alert", "Oferta de estágio não encontrada.");
        }
        return "redirect:/ofertas";
    }
}
