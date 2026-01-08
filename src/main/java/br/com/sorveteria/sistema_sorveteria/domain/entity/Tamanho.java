package br.com.sorveteria.sistema_sorveteria.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "TAMANHO")

public class Tamanho {

@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TAMANHO")
    private Long id;

@Column(name = "DESCRICAO", nullable = false)
    private String descricao;

@Column(name = "PRECO_BASE", nullable = false)
    private BigDecimal precoBase;

public void setDescricao(String descricao) {
    this.descricao = descricao;
}

public void setPrecoBase(BigDecimal precoBase) {
    this.precoBase = precoBase;

}

}
