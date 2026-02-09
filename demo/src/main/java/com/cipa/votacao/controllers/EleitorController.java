package com.cipa.votacao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cipa.votacao.model.Services.EleitorService;

@Controller
public class EleitorController {

    @Autowired
    private EleitorService eleitorService;

    @GetMapping("/eleitores")
    public ModelAndView viewEleitores() {
        ModelAndView mv = new ModelAndView("eleitores");
        mv.addObject("eleitores", eleitorService.findAll());
        return mv;
    }

}
