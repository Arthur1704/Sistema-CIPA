package com.cipa.votacao.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cipa.votacao.model.entidades.Eleitor;

public interface EleitorRepository extends JpaRepository<Eleitor, Long> {

}
