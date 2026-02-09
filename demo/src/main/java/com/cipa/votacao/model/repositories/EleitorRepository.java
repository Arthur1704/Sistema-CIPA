package com.cipa.votacao.model.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cipa.votacao.model.entidades.Eleitor;

public interface EleitorRepository extends JpaRepository<Eleitor, Long> {

    Optional<Eleitor> findByCpfAndDataNasc(String cpf, LocalDate data_nasc);

}
