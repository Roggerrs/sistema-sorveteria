package br.com.sorveteria.sistema_sorveteria.domain.dto;

import java.math.BigDecimal;

public class PedidoResponseDTO {

    private Long id;
    private String atendente;
    private BigDecimal valorTotal;

    public PedidoResponseDTO(Long id, String atendente, BigDecimal valorTotal) {
        this.id = id;
        this.atendente = atendente;
        this.valorTotal = valorTotal;
    }

    public Long getId() {
        return id;
    }

    public String getAtendente() {
        return atendente;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }
}
