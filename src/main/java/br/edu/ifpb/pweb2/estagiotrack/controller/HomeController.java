package br.edu.ifpb.pweb2.estagiotrack.controller;

import br.edu.ifpb.pweb2.estagiotrack.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @RequestMapping("/form")
    public String showHomePage() {
        return "index";
    }
}
