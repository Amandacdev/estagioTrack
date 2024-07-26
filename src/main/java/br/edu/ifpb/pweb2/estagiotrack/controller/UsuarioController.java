package br.edu.ifpb.pweb2.estagiotrack.controller;
import br.edu.ifpb.pweb2.estagiotrack.repository.UsuarioRepository;
import br.edu.ifpb.pweb2.estagiotrack.model.Empresa;
import br.edu.ifpb.pweb2.estagiotrack.model.Usuario;
import br.edu.ifpb.pweb2.estagiotrack.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @RequestMapping("/form")
    public String getForm(Empresa empresa, Model model){
        //model.addAttribute("usuario", usuario);
        return "usuarios/form";
    }

    @Autowired
    private EmpresaRepository empresaRepository;


    /*
    //Falta ajustar o redirect
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String cadastroUsuario(Usuario usuario, Model model, RedirectAttributes attr){
        if(usuario.getRazaoSocial().isEmpty() || usuario.getEmail().isEmpty() || usuario.getSenha().isEmpty()){
            model.addAttribute("alert", "Por favor preencha todos os campos corretamente.");
            return "usuarios/form";
        } else {
            usuarioRepository.save(usuario);
            model.addAttribute("usuarios",UsuarioRepository.findAll());
        }

    }

     */
}
