package br.edu.ifpb.pweb2.estagiotrack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import br.edu.ifpb.pweb2.estagiotrack.model.Candidatura;
import br.edu.ifpb.pweb2.estagiotrack.repository.CandidaturaRepository;

@Controller
public class CoordenadorController {

    private final String PIN_CORRETO = "1234"; // PIN mockado

    @Autowired
    private CandidaturaRepository candidaturaRepository;

    @GetMapping("/coordenador/acesso")
    public String acessoCoordenador() {
        return "coordenador/acesso"; 
    }

    @PostMapping("/coordenador/validar")
    public String validarPin(@RequestParam("pin") String pin, Model model) {
        if (PIN_CORRETO.equals(pin)) {
            return "redirect:/coordenador/dashboard"; 
        } else {
            model.addAttribute("erro", "PIN incorreto. Tente novamente.");
            return "coordenador/acesso";
        }
    }

    @GetMapping("/coordenador/dashboard")
    public String dashboardCoordenador() {
        return "coordenador/dashboard"; 
    }

    @GetMapping("/coordenador/candidaturas")
    public String listarCandidaturas(Model model) {
        // Busca todas as candidaturas usando o repositório
        List<Candidatura> candidaturas = candidaturaRepository.findAll();

        // Adiciona as candidaturas ao Model para disponibilizá-las na view
        model.addAttribute("candidaturas", candidaturas);

        // Retorna o nome do template que será renderizado
        return "candidaturas/list";
    }
}
