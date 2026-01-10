package br.com.sorveteria.sistema_sorveteria.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoDetalheResponseDTO {

    private Long id;
    private String atendente;
    private LocalDateTime dataPedido;
    private List<SorveteDetalheDTO> sorvetes;
    private BigDecimal total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAtendente() {
        return atendente;
    }

    public void setAtendente(String atendente) {
        this.atendente = atendente;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public List<SorveteDetalheDTO> getSorvetes() {
        return sorvetes;
    }

    public void setSorvetes(List<SorveteDetalheDTO> sorvetes) {
        this.sorvetes = sorvetes;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
