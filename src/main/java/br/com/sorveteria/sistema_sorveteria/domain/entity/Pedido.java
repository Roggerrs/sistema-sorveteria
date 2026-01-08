package br.com.sorveteria.sistema_sorveteria.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PEDIDO")

public class Pedido {

@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PEDIDO")
    private Long id;

@Column(name = "DATA_PEDIDO", nullable = false)
    private LocalDateTime dataPedido;

@ManyToOne
    @JoinColumn(name = "ATENDENTE_ID_ATENDENTE", nullable = false)
    private Atendente atendente;

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public Atendente getAtendente() {
        return atendente;
    }

    public void setAtendente(Atendente atendente) {
        this.atendente = atendente;
    }

}
