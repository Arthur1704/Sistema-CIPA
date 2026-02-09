package com.cipa.votacao.model.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "candidato")
public class Candidato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_candidato;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String cpf;

    public Candidato() {

    }

    public Candidato(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public Long getId_candidato() {
        return id_candidato;
    }

    public void setId_candidato(Long id_candidato) {
        this.id_candidato = id_candidato;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id_candidato == null) ? 0 : id_candidato.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Candidato other = (Candidato) obj;
        if (id_candidato == null) {
            if (other.id_candidato != null)
                return false;
        } else if (!id_candidato.equals(other.id_candidato))
            return false;
        return true;
    }

}
