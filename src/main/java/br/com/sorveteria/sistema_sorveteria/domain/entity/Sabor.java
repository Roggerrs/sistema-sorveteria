package br.com.sorveteria.sistema_sorveteria.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "SABOR")

public class Sabor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SABOR")
    private Long id;

    @Column(name = "NOME", nullable = false)
    private String nome;

    public Long getId() {
        return id;
    }

    public  String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;

    }
}
