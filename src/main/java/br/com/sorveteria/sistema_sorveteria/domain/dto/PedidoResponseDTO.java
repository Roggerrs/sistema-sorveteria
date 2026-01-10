package br.com.sorveteria.sistema_sorveteria.domain.dto;

import java.math.BigDecimal;

public class PedidoResponseDTO {

    private Long id;
    private String nomeAtendente;
    private BigDecimal valorTotal;

    public PedidoResponseDTO(Long id, String nomeAtendente, BigDecimal valorTotal) {
        this.id = id;
        this.nomeAtendente = nomeAtendente;
        this.valorTotal = valorTotal;
    }

    public Long getId() {
        return id;
    }

    public String getNomeAtendente() {
        return nomeAtendente;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }
}
