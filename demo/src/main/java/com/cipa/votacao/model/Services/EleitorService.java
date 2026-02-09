package com.cipa.votacao.model.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.cipa.votacao.model.entidades.Eleitor;
import com.cipa.votacao.model.repositories.EleitorRepository;

@Service
public class EleitorService {

    @Autowired
    private EleitorRepository eleitorRepository;

    public void insert(Eleitor eleitor) {
        try {
            eleitorRepository.save(eleitor);
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            eleitorRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Eleitor findById(Long id) {
        return eleitorRepository.findById(id).orElseThrow(() -> new RuntimeException("Candidato não encontrado"));
    }

    public List<Eleitor> findAll() {
        try {
            return eleitorRepository.findAll();
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void update(Eleitor eleitor) {
        Eleitor eleitor2 = eleitorRepository.findById(eleitor.getId_eleitor())
                .orElseThrow(() -> new RuntimeException("Candidato não existe."));
        eleitor2.setCpf(eleitor.getCpf());
        eleitor2.setData_nasc(eleitor.getData_nasc());
        eleitor2.setSecretaria(eleitor.getSecretaria());
        eleitorRepository.save(eleitor2);
    }

}
