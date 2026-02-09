package com.cipa.votacao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cipa.votacao.model.Services.CandidatoService;

@Controller
public class CandidatoController {

    @Autowired
    private CandidatoService candidatoService;

    @GetMapping("/candidatos")
    public ModelAndView viewCandidatos() {
        ModelAndView mv = new ModelAndView("candidatos");
        mv.addObject("candidatos", candidatoService.findAll());
        return mv;
    }

}
