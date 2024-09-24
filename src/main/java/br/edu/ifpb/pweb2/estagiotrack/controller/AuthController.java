package br.edu.ifpb.pweb2.estagiotrack.controller;

import br.edu.ifpb.pweb2.estagiotrack.model.Aluno;
import br.edu.ifpb.pweb2.estagiotrack.repository.AlunoRepository;
import br.edu.ifpb.pweb2.estagiotrack.util.PasswordUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AlunoRepository alunoRepositorio;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getForm(ModelAndView modelAndView) {
        modelAndView.setViewName("auth/login");
        modelAndView.addObject("aluno", new Aluno());
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView valide(Aluno aluno, HttpSession session, ModelAndView modelAndView, RedirectAttributes redirectAttrs) {
        if ((aluno = this.isValido(aluno)) != null){
            session.setAttribute("aluno", aluno);
            modelAndView.setViewName("redirect:/home");
        } else {
            redirectAttrs.addFlashAttribute("mensagem","Login e/ou senha inválidos");
            modelAndView.setViewName("redirect:/auth");
        }
        return modelAndView;
    }

    @RequestMapping("/logout")
    public ModelAndView logout(ModelAndView mav, HttpSession session) {
        session.invalidate();
        mav.setViewName("redirect:/auth");
        return mav;
    }

    private Aluno isValido(Aluno aluno) {
        Optional<Aluno> alunoBD = alunoRepositorio.findByEmail(aluno.getEmail());

        if (alunoBD.isPresent()) {
            if (PasswordUtil.checkPass(aluno.getSenha(), alunoBD.get().getSenha())) {
                return alunoBD.get();
            }
        }
        return null;

    }


}
