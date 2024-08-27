package br.edu.ifpb.pweb2.estagiotrack.controller;
import br.edu.ifpb.pweb2.estagiotrack.model.Aluno;
import br.edu.ifpb.pweb2.estagiotrack.model.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import br.edu.ifpb.pweb2.estagiotrack.service.AlunoService;
import br.edu.ifpb.pweb2.estagiotrack.service.EmpresaService;

@Controller
@RequestMapping("/")
public class LoginController {
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @RequestMapping("/formParaFiltroPesquisa")
    // @GetMapping("/formParaFiltroPesquisa")
    public String showFiltroPage() {
        return "consultaEstudanteEmpresa";
    }

    /*
     * Recebe um email e verifica:
     * Se o email é de um estudante, direciona para a página que exibe as
     * candidatutas do estudante solicitante
     * Se o email é de uma empresa, direciona para a página que exibe as ofertas da
     * empresa solicitante, com buttons de ação
     * Se não identificado, mensagem de erro
     */
    @PostMapping("/identificar")
    public String pesquisarSolicitante(@RequestParam("emailFiltro") String emailFiltro, Model model) {

        // Checando se tem estudante com esse email
        Aluno aluno = alunoService.findByEmail(emailFiltro).orElse(null);

        // Checando se tem empresa com esse email
        Empresa empresa = empresaService.findByEmail(emailFiltro).orElse(null);

        if (aluno != null && empresa == null) {
            return "redirect:/paginaUsuario/estudantePage";
        }
        if (aluno == null && empresa != null) {
            return "redirect:/paginaUsuario/empresaPage";
        } else {
            model.addAttribute("erro", "Não foi encontrado um cadastro com esse email. Tente novamente.");
            return "redirect:/formParaFiltroPesquisa";
            // ajustar para funcionar de forma a exibir a mensagem de erro: return
            // "formParaFiltroPesquisa";
        }
    }
}
