package com.cipa.votacao.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cipa.votacao.model.Services.EleitorService;

@Controller
public class AuthController {

    @Autowired
    private EleitorService login;

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @PostMapping("/login")
    public ModelAndView logar(@RequestParam String cpf, @RequestParam LocalDate data) {
        if (login.validateLogin(cpf, data)) {
            return new ModelAndView("redirect:/votacao");
        }

        ModelAndView mv = new ModelAndView("index");
        mv.addObject("error", "CPF ou data de nascimento inválidos");
        return mv;
    }
}
