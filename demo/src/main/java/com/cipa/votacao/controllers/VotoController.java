package com.cipa.votacao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cipa.votacao.model.DTO.VotoDTO;
import com.cipa.votacao.model.Services.CandidatoService;
import com.cipa.votacao.model.Services.VotoService;

@Controller
public class VotoController {

    @Autowired
    public VotoService votoService;

    @Autowired
    public CandidatoService candidatoService;

    @GetMapping("/votacao")
    public ModelAndView viewVotos() {
        ModelAndView mv = new ModelAndView("votar");
        mv.addObject("votoDTO", new VotoDTO());
        mv.addObject("candidatos", candidatoService.findAll());
        mv.addObject("votos", votoService.contarVotosPorCandidato());
        return mv;
    }

    @PostMapping("/votar")
    public ModelAndView votar(@ModelAttribute("votoDTO") VotoDTO votoDTO) {
        votoService.votar(votoDTO);
        return new ModelAndView("redirect:/resultado");
    }

    @GetMapping("/resultado")
    public ModelAndView resultado() {
        ModelAndView mv = new ModelAndView("resultado-votacao");
        mv.addObject("ranking", votoService.contarVotosPorCandidato());
        return mv;
    }

}
