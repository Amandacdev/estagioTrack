package br.edu.ifpb.pweb2.estagiotrack.controller;
import br.edu.ifpb.pweb2.estagiotrack.model.*;
import br.edu.ifpb.pweb2.estagiotrack.service.AlunoService;
import br.edu.ifpb.pweb2.estagiotrack.service.CandidaturaService;
import br.edu.ifpb.pweb2.estagiotrack.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifpb.pweb2.estagiotrack.model.Candidatura;
import br.edu.ifpb.pweb2.estagiotrack.repository.CandidaturaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/paginaUsuario")
@RequiredArgsConstructor
public class paginaUsuarioController {

    @RequestMapping("/formParaFiltroPesquisa")
    public String showFiltroPage() {
        return "consultaEstudanteEmpresa";
    }


    // variáveis que serão usadas na verificação se existe o email buscado no filtro
    @Autowired
    private AlunoService alunoService;
    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private CandidaturaController candidaturaController;

    private final CandidaturaRepository candidaturaRepository;

    @Autowired
    public paginaUsuarioController(AlunoService alunoService, EmpresaService empresaService, CandidaturaRepository candidaturaRepository) {
        this.alunoService = alunoService;
        this.empresaService = empresaService;
        this.candidaturaRepository = candidaturaRepository;
    }


    /*Recebe um email e verifica:
        Se o email é de um estudante, direciona para a página que exibe as candidatutas do estudante solicitante
        Se o email é de uma empresa, direciona para a página que exibe as ofertas da empresa solicitante, com buttons de ação
        Se não identificado, mensagem de erro*/

    @PostMapping("/identificar")
    public String pesquisarSolicitante(@RequestParam("emailFiltro") String emailFiltro, Model model) {

        // Checando se tem estudante com esse email
        Aluno aluno = alunoService.findByEmail(emailFiltro).orElse(null);

        // Checando se tem empresa com esse email
        Empresa empresa = empresaService.findByEmail(emailFiltro).orElse(null);

        if (aluno != null && empresa == null){
            candidaturaController.getListCandidaturasUsuario(model,aluno);
            //return "redirect:/paginaUsuario/estudantePage?email=" + emailFiltro;
        }if (aluno == null && empresa != null){
            return "redirect:/paginaUsuario/empresaPage";
        }
        else {
            model.addAttribute("erro", "Não foi encontrado um cadastro com esse email. Tente novamente.");
            return "redirect:/formParaFiltroPesquisa";
            //ajustar para funcionar de forma a exibir a mensagem de erro: return "formParaFiltroPesquisa";
        }
    }
    /*
    @RequestMapping("estudantePage")
    public String getEstudantePage(@RequestParam("emailFiltro") String emailFiltro, Model model) {

        // Buscando o estudante pelo email
        Aluno aluno = alunoService.findByEmail(emailFiltro).orElse(null);

        // Verificando se o aluno foi encontrado
        if (aluno == null) {
            model.addAttribute("erro", "Estudante não encontrado.");
            return "paginaUsuario/candidaturasEstudante"; // Ou redirecionar para uma página de erro
        }

        // Buscando as candidaturas do estudante
        List<Candidatura> candidaturasDoUsuario = candidaturaRepository.findByEmail(emailFiltro);

        // Adicionando as candidaturas e os dados do aluno ao modelo
        model.addAttribute("aluno", aluno);
        model.addAttribute("candidaturas", candidaturasDoUsuario);

        return "paginaUsuario/candidaturasEstudante";
    }

    */

        @RequestMapping("empresaPage")
    public String getEmpresaPage() {
        return "paginaUsuario/ofertasEmpresa";
    }

}

