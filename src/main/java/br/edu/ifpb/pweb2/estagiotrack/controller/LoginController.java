package br.edu.ifpb.pweb2.estagiotrack.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class LoginController {
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
}