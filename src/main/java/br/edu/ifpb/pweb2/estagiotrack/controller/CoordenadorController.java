package br.edu.ifpb.pweb2.estagiotrack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.ifpb.pweb2.estagiotrack.model.Candidatura;
import br.edu.ifpb.pweb2.estagiotrack.model.Paginador;
import br.edu.ifpb.pweb2.estagiotrack.service.CandidaturaService;

@Controller
public class CoordenadorController {

    private final String PIN_CORRETO = "1234"; // PIN mockado

    @Autowired
    private CandidaturaService candidaturaService;

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
    public String listarCandidaturas(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Candidatura> candidaturasPage = candidaturaService.listAll(pageable);

        Paginador paginador = new Paginador(
                candidaturasPage.getNumber(),
                candidaturasPage.getSize(),
                (int) candidaturasPage.getTotalElements());

        model.addAttribute("candidaturas", candidaturasPage);
        model.addAttribute("paginador", paginador);

        return "candidaturas/list";
    }
}
