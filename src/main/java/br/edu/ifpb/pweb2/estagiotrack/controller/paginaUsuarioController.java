package br.edu.ifpb.pweb2.estagiotrack.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/paginaUsuario")
@RequiredArgsConstructor
public class paginaUsuarioController {

    @RequestMapping("estudantePage")
    public String getEstudantePage() {
        return "paginaUsuario/candidaturasEstudante";
    }

    @RequestMapping("empresaPage")
    public String getEmpresaPage() {
        return "paginaUsuario/ofertasEmpresa";
    }

}
