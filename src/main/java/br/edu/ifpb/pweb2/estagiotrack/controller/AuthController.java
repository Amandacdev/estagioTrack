package br.edu.ifpb.pweb2.estagiotrack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.estagiotrack.model.User;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getForm(ModelAndView modelAndView) {
        modelAndView.setViewName("auth/login");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }
}