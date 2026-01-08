package br.com.sorveteria.sistema_sorveteria.domain.dto;

public class PedidoResponseDTO {

    private Long pedidoId;
    private String nomeAtendente;

    public PedidoResponseDTO(Long pedidoId, String nomeAtendente) {
        this.pedidoId = pedidoId;
        this.nomeAtendente = nomeAtendente;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public String getNomeAtendente() {
        return nomeAtendente;
    }
}
