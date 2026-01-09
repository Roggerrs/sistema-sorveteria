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

    @Column(name = "PRECO_TAMANHO", nullable = false)
    private BigDecimal precoTamanho;

    public Long getId() {
        return id;
    }

    // 🔴 GETTER OBRIGATÓRIO
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPrecoTamanho() {
        return precoTamanho;
    }

    public void setPrecoTamanho(BigDecimal precoTamanho) {
        this.precoTamanho = precoTamanho;
    }


}
