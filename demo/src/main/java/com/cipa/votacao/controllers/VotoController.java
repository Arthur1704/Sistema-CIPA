package com.cipa.votacao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cipa.votacao.model.DTO.VotoDTO;
import com.cipa.votacao.model.Services.CandidatoService;
import com.cipa.votacao.model.Services.EleitorService;
import com.cipa.votacao.model.Services.VotoService;
import com.cipa.votacao.model.entidades.Eleitor;

import jakarta.servlet.http.HttpSession;

@Controller
public class VotoController {

    @Autowired
    public VotoService votoService;

    @Autowired
    public CandidatoService candidatoService;

    @Autowired
    public EleitorService eleitorService;

    @GetMapping("/votacao")
    public ModelAndView viewVotos() {
        ModelAndView mv = new ModelAndView("votar");
        mv.addObject("votoDTO", new VotoDTO());

        var candidatos = candidatoService.findAll();
        candidatos.forEach(c -> {
            if (c.getFoto() != null && c.getFoto().length > 0) {
                String base64 = java.util.Base64.getEncoder().encodeToString(c.getFoto());
                c.setFotoBase64(base64);
            }
        });

        mv.addObject("candidatos", candidatos);
        mv.addObject("votos", votoService.contarVotosPorCandidato());
        return mv;
    }

    @PostMapping("/votar")
    public ModelAndView votar(@ModelAttribute("votoDTO") VotoDTO votoDTO, HttpSession session) {

        Long idEleitor = (Long) session.getAttribute("usuarioLogadoId");

        if (idEleitor == null) {
            return new ModelAndView("redirect:/"); // Volta para o login se a sessão expirou
        }

        Eleitor eleitor = eleitorService.findById(idEleitor);

        if (eleitor.getJa_votou()) {
            ModelAndView mv = new ModelAndView("redirect:/resultado");
            mv.addObject("error", "Você já realizou seu voto!");
            return mv;
        }

        eleitor.setJa_votou(true);
        eleitorService.update(eleitor);

        votoService.votar(votoDTO);

        return new ModelAndView("redirect:/resultado");
    }

    @GetMapping("/resultado")
    public ModelAndView resultado() {
        ModelAndView mv = new ModelAndView("resultado");
        mv.addObject("ranking", votoService.contarVotosPorCandidato());
        return mv;
    }

}
