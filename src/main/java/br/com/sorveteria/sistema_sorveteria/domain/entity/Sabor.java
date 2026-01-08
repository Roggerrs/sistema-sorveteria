package br.com.sorveteria.sistema_sorveteria.domain.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "SABOR")
public class Sabor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SABOR")
    private Long id;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "PRECO_ADICIONAL", nullable = false)
    private BigDecimal precoAdicional;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPrecoAdicional() {
        return precoAdicional;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPrecoAdicional(BigDecimal precoAdicional) {
        this.precoAdicional = precoAdicional;
    }
}

