package com.cipa.votacao.model.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.cipa.votacao.model.entidades.Candidato;
import com.cipa.votacao.model.repositories.CandidatoRepository;

import jakarta.transaction.Transactional;

@Service
public class CandidatoService {

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Transactional
    public void insert(Candidato candidato) {
        try {
            candidatoRepository.save(candidato);
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            candidatoRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Candidato findById(Long id) {
        return candidatoRepository.findById(id).orElseThrow(() -> new RuntimeException("Candidato não encontrado"));
    }

    public List<Candidato> findAll() {
        try {
            return candidatoRepository.findAll();
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public void update(Candidato candidato) {
        Candidato candidato2 = candidatoRepository.findById(candidato.getId_candidato())
                .orElseThrow(() -> new RuntimeException("Candidato não existe."));
        candidato2.setCpf(candidato.getCpf());
        candidato2.setNome(candidato.getNome());
        candidatoRepository.save(candidato2);
    }

}
