package br.com.sorveteria.sistema_sorveteria.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ATENDENTE")

public class Atendente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ATENDENTE")
    private Long id;

    @Column(name = "NOME", nullable = false)
    private String nome;

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getNome() {
    return nome;
}

public void setNome(String nome) {
    this.nome = nome;
}

}
