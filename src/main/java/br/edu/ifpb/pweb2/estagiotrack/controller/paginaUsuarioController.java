package br.edu.ifpb.pweb2.estagiotrack.controller;

import br.edu.ifpb.pweb2.estagiotrack.model.*;
import br.edu.ifpb.pweb2.estagiotrack.service.AlunoService;
import br.edu.ifpb.pweb2.estagiotrack.service.EmpresaService;
import br.edu.ifpb.pweb2.estagiotrack.service.OfertaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/paginaUsuario")
@RequiredArgsConstructor
public class paginaUsuarioController {

    @RequestMapping("/formParaFiltroPesquisa")
    public String showFiltroPage() {
        return "consultaEstudanteEmpresa";
    }

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private CandidaturaController candidaturaController;

    @Autowired
    private OfertaController ofertaController;

    //Esse método localiza o usuário que deseja ver suas ofertas(ou)candidaturas e chama o método adequado para a obtenção das mesmas
    @PostMapping("/identificar")
    public String pesquisarSolicitante(@RequestParam("emailFiltro") String emailFiltro, Model model) {

        // Checando se tem estudante com esse email
        Aluno aluno = alunoService.findByEmail(emailFiltro).orElse(null);

        // Checando se tem empresa com esse email
        Empresa empresa = empresaService.findByEmail(emailFiltro).orElse(null);

        //Estudante localizado
        if (aluno != null && empresa == null) {
            candidaturaController.getListCandidaturasUsuario(model, aluno);
            return "paginaUsuario/candidaturasEstudante";
        }
        //Empresa localizada
        if (aluno == null && empresa != null) {
           ofertaController.getListOfertasUsuario(model,empresa);
            //OfertaController.getListOfertasUsuario(model, empresa);
            return "paginaUsuario/ofertasEmpresa";
        }
        //Email sem cadastro
        else {
            model.addAttribute("erro", "Não foi encontrado um cadastro com esse email. Tente novamente.");
            return "redirect:/paginaUsuario/formParaFiltroPesquisa";
            // ajustar para funcionar de forma a exibir a mensagem de erro: return
            // "formParaFiltroPesquisa";
        }
    }

    @RequestMapping("empresaPage")
    public String getEmpresaPage() {
        return "paginaUsuario/ofertasEmpresa";
    }

}

