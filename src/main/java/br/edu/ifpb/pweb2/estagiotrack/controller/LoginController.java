package br.edu.ifpb.pweb2.estagiotrack.controller;

import br.edu.ifpb.pweb2.estagiotrack.model.Aluno;
import br.edu.ifpb.pweb2.estagiotrack.model.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private AlunoController alunoController;

    @Autowired
    private EmpresaController empresaController;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @RequestMapping("/formParaFiltroPesquisa")
    public String showFiltroPage() {
        return "consultaEstudanteEmpresa";
    }

    @PostMapping("/identificar")
    public String pesquisarSolicitante(@RequestParam("emailFiltro") String emailFiltro, Model model) {

        // Checando se tem estudante com esse email
        Aluno aluno = alunoController.buscarPorEmail(emailFiltro);

        // Checando se tem empresa com esse email
        Empresa empresa = empresaController.buscarPorEmail(emailFiltro);

        if (aluno != null) {
            return "redirect:/paginaUsuario/estudantePage";
        }
        if (empresa != null) {
            return "redirect:/paginaUsuario/empresaPage";
        } else {
            model.addAttribute("erro", "Não foi encontrado um cadastro com esse email. Tente novamente.");
            return "consultaEstudanteEmpresa";
        }
    }

    @PostMapping("/validar")
    public String login(@RequestParam String email, @RequestParam String senha, ModelAndView modelAndView, RedirectAttributes attr) {
      return "/login";
        /*
        if (usuarioService.validarLogin(email, senha)) {
            return "";
        } else {
            // Login falhou, mostrar erro
            attr.addFlashAttribute("erro", "Email ou senha inválidos");
            return "";
        }

       */
    }

}
