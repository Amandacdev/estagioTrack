package br.edu.ifpb.pweb2.estagiotrack.controller;

import br.edu.ifpb.pweb2.estagiotrack.model.Empresa;
import br.edu.ifpb.pweb2.estagiotrack.model.Oferta;
import br.edu.ifpb.pweb2.estagiotrack.repository.EmpresaRepository;
import br.edu.ifpb.pweb2.estagiotrack.repository.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ofertas")
public class OfertaController {

    @Autowired
    public OfertaRepository ofertaRepository;

    @Autowired
    private EmpresaController empresaController;

    @Autowired
    private EmpresaRepository empresaRepository;

    @RequestMapping("/form")
    public String getForm(Oferta oferta, Model model) {
        //Para facilitar os testes estou iniciando o projeto com 2 ofertas sendo inseridas ao acessar cadastrar oferta e voltar.
        ofertaRepository.save(new Oferta(1, empresaRepository.findById(1),"responsavela@empresa.com", "Exemplo Front-End", "1000", "Manhã"));
        ofertaRepository.save(new Oferta(2, empresaRepository.findById(2),"responsavelb@empresa.com", "Exemplo Back-End", "1000", "Tarde"));
        ofertaRepository.save(new Oferta(3, empresaRepository.findById(3),"responsavelc@empresa.com", "Exemplo Full Stack", "1000", "A combinar"));
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

    //Parte do workaround para vincular oferta a empresa dinamicamente.
    public Oferta buscarPorId(Integer id) {
        Oferta oferta = ofertaRepository.findById(id);
        if (oferta != null) {
            return oferta;
        } else {
            return null;
        }
    }
}
