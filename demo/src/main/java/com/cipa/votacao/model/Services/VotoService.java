package com.cipa.votacao.model.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cipa.votacao.model.DTO.ResultadoVotoDTO;
import com.cipa.votacao.model.DTO.VotoDTO;
import com.cipa.votacao.model.entidades.Candidato;
import com.cipa.votacao.model.entidades.Voto;
import com.cipa.votacao.model.repositories.CandidatoRepository;
import com.cipa.votacao.model.repositories.VotoRepository;

import jakarta.transaction.Transactional;

@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Transactional
    public void votar(VotoDTO votoDTO) {

        Candidato candidato = candidatoRepository.findById(votoDTO.getIdCandidato())
                .orElseThrow(() -> new RuntimeException("Candidato não encontrado"));

        Voto voto = new Voto();

        voto.setCandidato(candidato);
        votoRepository.save(voto);
    }

    public List<ResultadoVotoDTO> contarVotosPorCandidato() {
        return votoRepository.contarVotosPorCandidato();
    }

}
