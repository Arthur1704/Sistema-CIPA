package com.cipa.votacao.model.entidades;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "eleitor")
public class Eleitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_eleitor;

    @Column(nullable = false, unique = true)
    private String cpf;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_nasc", nullable = false)
    private LocalDate dataNasc;

    @Column(nullable = false)
    private String secretaria;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean ja_votou = false;

    public Eleitor(String cpf, LocalDate dataNasc, String secretaria) {
        this.cpf = cpf;
        this.dataNasc = dataNasc;
        this.secretaria = secretaria;
    }

    public Long getId_eleitor() {
        return id_eleitor;
    }

    public void setId_eleitor(Long id_eleitor) {
        this.id_eleitor = id_eleitor;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getdataNasc() {
        return dataNasc;
    }

    public void setdataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getSecretaria() {
        return secretaria;
    }

    public void setSecretaria(String secretaria) {
        this.secretaria = secretaria;
    }

    public boolean isJa_votou() {
        return ja_votou;
    }

    public void setJa_votou(boolean ja_votou) {
        this.ja_votou = ja_votou;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id_eleitor == null) ? 0 : id_eleitor.hashCode());
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
        Eleitor other = (Eleitor) obj;
        if (id_eleitor == null) {
            if (other.id_eleitor != null)
                return false;
        } else if (!id_eleitor.equals(other.id_eleitor))
            return false;
        return true;
    }

}
