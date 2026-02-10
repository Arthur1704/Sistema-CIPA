package com.cipa.votacao.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cipa.votacao.model.Services.CandidatoService;
import com.cipa.votacao.model.Services.EleitorService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private EleitorService login;

    @Autowired
    private CandidatoService candidatoService;

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @PostMapping("/login")
    public ModelAndView logar(@RequestParam String cpf, @RequestParam LocalDate data, HttpSession session) {
        if (login.validateLogin(cpf, data)) {
            var eleitor = login.findByCpf(cpf);
            if (eleitor.getJa_votou()) {
                return new ModelAndView("redirect:/resultado");
            }
            session.setAttribute("usuarioLogadoId", eleitor.getId_eleitor());
            ModelAndView mv = new ModelAndView("redirect:/votacao");
            mv.addObject("candidatos", candidatoService.findAll());
            return mv;
        }

        ModelAndView mv = new ModelAndView("index");
        mv.addObject("error", "CPF ou data de nascimento inválidos");
        return mv;
    }
}
