package com.cipa.votacao.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cipa.votacao.model.entidades.Candidato;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
}
