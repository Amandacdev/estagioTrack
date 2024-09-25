package br.edu.ifpb.pweb2.estagiotrack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/home")
    public String showHomePage(Model model) {
        //model.addAttribute("menu", "home");
        return "/home";
    }

    /*
    @RequestMapping("/acesso-negado")
    public String getAcessoNegado(Model model) {
        model.addAttribute("menu", "home");
        return "/acesso-negado";
    }
    */

}
