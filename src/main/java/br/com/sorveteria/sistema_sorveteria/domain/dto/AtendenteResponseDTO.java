package br.com.sorveteria.sistema_sorveteria.domain.dto;

public class AtendenteResponseDTO {

    private Long id;
    private String nome;

    public AtendenteResponseDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
