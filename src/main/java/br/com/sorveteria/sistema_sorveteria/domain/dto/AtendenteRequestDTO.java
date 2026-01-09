package br.com.sorveteria.sistema_sorveteria.domain.dto;

import jakarta.validation.constraints.NotBlank;

public class AtendenteRequestDTO {

    @NotBlank
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
