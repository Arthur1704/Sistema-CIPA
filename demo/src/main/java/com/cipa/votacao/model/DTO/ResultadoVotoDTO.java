package com.cipa.votacao.model.DTO;

public class ResultadoVotoDTO {

    private String nome;
    private Long total;

    public ResultadoVotoDTO(String nome, Long total) {
        this.nome = nome;
        this.total = total;
    }

    public String getNome() {
        return nome;
    }

    public Long getTotal() {
        return total;
    }

}
