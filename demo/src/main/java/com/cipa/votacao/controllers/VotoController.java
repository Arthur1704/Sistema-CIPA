package com.cipa.votacao.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cipa.votacao.model.DTO.ResultadoVotoDTO;
import com.cipa.votacao.model.DTO.VotoDTO;
import com.cipa.votacao.model.Services.CandidatoService;
import com.cipa.votacao.model.Services.EleitorService;
import com.cipa.votacao.model.Services.VotoService;
import com.cipa.votacao.model.entidades.Eleitor;

import jakarta.servlet.http.HttpServletResponse;
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
    public ModelAndView viewVotos(HttpSession session, HttpServletResponse response) {

        // Força o navegador a não salvar a página em cache
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setHeader("Expires", "0"); // Proxies

        Long idEleitor = (Long) session.getAttribute("usuarioLogadoId");

        if (idEleitor == null)
            return new ModelAndView("redirect:/");

        Eleitor eleitor = eleitorService.findById(idEleitor);

        if (eleitor.getJa_votou()) {
            ModelAndView mv = new ModelAndView("redirect:/resultado");
            mv.addObject("error", "Você já realizou seu voto!");
            return mv;
        }

        ModelAndView mv = new ModelAndView("votar");
        mv.addObject("votoDTO", new VotoDTO());

        var candidatos = candidatoService.findAll().stream()
                .filter(c -> c.getIdCandidato() != 7 && c.getIdCandidato() != 8)
                .toList();
        // ------------------------------

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
            return new ModelAndView("redirect:/");
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

        /*
         * ModelAndView mv = new ModelAndView("resultado");
         * List<ResultadoVotoDTO> rankingCompleto =
         * votoService.contarVotosPorCandidato();
         * 
         * long totalBrancos = rankingCompleto.stream().filter(r ->
         * r.getNome().equalsIgnoreCase("VOTO EM BRANCO"))
         * .mapToLong(ResultadoVotoDTO::getTotal).sum();
         * 
         * long totalNulos = rankingCompleto.stream().filter(r ->
         * r.getNome().equalsIgnoreCase("VOTO NULO"))
         * .mapToLong(ResultadoVotoDTO::getTotal).sum();
         * 
         * List<ResultadoVotoDTO> candidatosReais = rankingCompleto.stream().filter(
         * r -> !r.getNome().equalsIgnoreCase("VOTO EM BRANCO") &&
         * !r.getNome().equalsIgnoreCase("VOTO NULO"))
         * .toList();
         * 
         * // Calcula o total no Java
         * long totalVotos = rankingCompleto.stream()
         * .mapToLong(ResultadoVotoDTO::getTotal)
         * .sum();
         * 
         * mv.addObject("ranking", candidatosReais);
         * mv.addObject("totalVotos", totalVotos);
         * mv.addObject("qtdBrancos", totalBrancos); // Vai para o card separado
         * mv.addObject("qtdNulos", totalNulos);
         * 
         * return mv;
         */

        return new ModelAndView("agradecimento");
    }

    @GetMapping("/final")
    public ModelAndView viewFinal() {
        ModelAndView mv = new ModelAndView("resultado");
        List<ResultadoVotoDTO> rankingCompleto = votoService.contarVotosPorCandidato();

        long totalBrancos = rankingCompleto.stream().filter(r -> r.getNome().equalsIgnoreCase("VOTO EM BRANCO"))
                .mapToLong(ResultadoVotoDTO::getTotal).sum();

        long totalNulos = rankingCompleto.stream().filter(r -> r.getNome().equalsIgnoreCase("VOTO NULO"))
                .mapToLong(ResultadoVotoDTO::getTotal).sum();

        List<ResultadoVotoDTO> candidatosReais = rankingCompleto.stream().filter(
                r -> !r.getNome().equalsIgnoreCase("VOTO EM BRANCO") &&
                        !r.getNome().equalsIgnoreCase("VOTO NULO"))
                .toList();

        // Calcula o total no Java
        long totalVotos = rankingCompleto.stream()
                .mapToLong(ResultadoVotoDTO::getTotal)
                .sum();

        mv.addObject("ranking", candidatosReais);
        mv.addObject("totalVotos", totalVotos);
        mv.addObject("qtdBrancos", totalBrancos); // Vai para o card separado
        mv.addObject("qtdNulos", totalNulos);

        return mv;

    }
}
