// package br.edu.ifpb.pweb2.estagiotrack.controller;

// import br.edu.ifpb.pweb2.estagiotrack.model.*;
// import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;

// @Controller
// @RequestMapping("/paginaUsuario")
// @RequiredArgsConstructor
// public class paginaUsuarioController {

//     @RequestMapping("/formParaFiltroPesquisa")
//     public String showFiltroPage() {
//         return "consultaEstudanteEmpresa";
//     }

//     @Autowired
//     private AlunoController alunoController;

//     @Autowired
//     private EmpresaController empresaController;

//     @Autowired
//     private CandidaturaController candidaturaController;

//     @Autowired
//     private OfertaController ofertaController;

//     // Esse método localiza o usuário que deseja ver suas ofertas(ou)candidaturas e
//     // chama o método adequado para a obtenção das mesmas
//     @PostMapping("/identificar")
//     public String pesquisarSolicitante(@RequestParam("emailFiltro") String emailFiltro, Model model) {

//         // Checando se tem estudante com esse email
//         Aluno aluno = alunoController.buscarPorEmail(emailFiltro);

//         // Checando se tem empresa com esse email
//         Empresa empresa = empresaController.buscarPorEmail(emailFiltro);

//         // Estudante localizado
//         // if (aluno != null) {
//         //     return candidaturaController.getListCandidaturasUsuario(model, aluno);
//         // }
//         // Empresa localizada
//         // if (empresa != null) {
//         //     return ofertaController.getListOfertasUsuario(model, empresa);
//         // }
//         // Email sem cadastro
//         // else {
//         //     model.addAttribute("erro", "Não foi encontrado um cadastro com esse email. Tente novamente.");
//         //     return "consultaEstudanteEmpresa";
//         // }
//     }

//     @RequestMapping("empresaPage")
//     public String getEmpresaPage() {
//         return "paginaUsuario/ofertasEmpresa";
//     }

// }
