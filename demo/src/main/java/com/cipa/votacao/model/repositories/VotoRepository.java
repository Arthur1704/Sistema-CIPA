package com.cipa.votacao.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cipa.votacao.model.DTO.ResultadoVotoDTO;
import com.cipa.votacao.model.entidades.Voto;

public interface VotoRepository extends JpaRepository<Voto, Long> {

    @Query("""
            SELECT new com.cipa.votacao.model.dto.ResultadoVotoDTO(
                v.candidato.nome,
                COUNT(v)
            )
            FROM Voto v
            GROUP BY v.candidato.nome
            ORDER BY COUNT(v) DESC
            """)
    List<ResultadoVotoDTO> contarVotosPorCandidato();

}
