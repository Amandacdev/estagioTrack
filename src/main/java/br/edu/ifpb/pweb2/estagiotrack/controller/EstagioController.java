package br.edu.ifpb.pweb2.estagiotrack.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.estagiotrack.model.Estagio;
import br.edu.ifpb.pweb2.estagiotrack.service.EstagioService;

@Controller
@RequestMapping("/estagios")
public class EstagioController {
    
    @Autowired
    public OfertaController ofertaController;

    @Autowired
    public AlunoController alunoController;

    @Autowired
    public EstagioService estagioService;

    @RequestMapping("/form")
    public String getForm(Estagio estagio, Model model) {
        return "estagios/form";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getList(Model model) {
        List<Estagio> estagios;
        estagios = estagioService.findAll();
        model.addAttribute("estagios", estagios);
        return "estagios/list";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String cadastroEstagio(Estagio estagio, Model model, RedirectAttributes attr) {
        if (estagio.getAlunoAprovado() == null || estagio.getOfertaSelecionada() == null) {
            model.addAttribute("alert", "Erro. Selecione um estudante e/ou oferta válido(a)");
        } else {
            if (estagio.getId() == null) {
                Integer maxId = estagioService.findMaxId();
                estagio.setId(maxId + 1);
            }
            attr.addFlashAttribute("success", "Estágio cadastrado com sucesso");
            return "redirect:/estagios/detalhes/" + estagio.getId();
        }
        model.addAttribute("alert", "Erro ao salvar estágio.");
        return "/";
    }

    public Estagio buscarPorId(Integer id) {
        Optional<Estagio> estagio = estagioService.findById(id);
        return estagio.orElse(null);
    }

    @RequestMapping("/detalhes/{id}")
    public String getDetalhesEstagio(@PathVariable("id") Integer id, Model model) {
        Optional<Estagio> estagio = estagioService.findById(id);
        if (estagio.isPresent()) {
            model.addAttribute("estagio", estagio.get());
            return "estagios/detalhes";
        } else {
            model.addAttribute("alert", "Estágio não encontrado");
            return "redirect:/estagios";
        }
    }
}
