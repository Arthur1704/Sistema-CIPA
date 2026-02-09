package com.cipa.votacao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cipa.votacao.model.Services.CandidatoService;
import com.cipa.votacao.model.Services.VotoService;

@Controller
public class VotoController {

    @Autowired
    public VotoService votoService;

    @Autowired
    public CandidatoService candidatoService;

}
