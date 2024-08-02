package br.edu.ifpb.pweb2.estagiotrack.controller;

import br.edu.ifpb.pweb2.estagiotrack.model.Oferta;
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


    @RequestMapping("/form")
    public String getForm(Oferta oferta, Model model){
        model.addAttribute("oferta", oferta);
        return "ofertas/form";
    }

    @Autowired
    private OfertaRepository ofertaRepository;

    //Falta ajustar o redirect
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String cadastroOferta(Oferta oferta, Model model, RedirectAttributes attr){
        if(oferta.getTituloCargo().isEmpty() || oferta.getOfertante().getRazaoSocial().isEmpty() || oferta.horarios.isEmpty()){
            model.addAttribute("alert", "Por favor preencha todos os campos corretamente.");
            return "ofertas/form";
        } else {
            ofertaRepository.save(oferta);
            model.addAttribute("ofertas",ofertaRepository.findAll());
            return "ofertas/list";
            //attr.addFlashAttribute("mensagem", "Aluno cadastrado com sucesso!");
            //return "redirect:/alunos/list";
        }

    }
}
