package br.com.sorveteria.sistema_sorveteria.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
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

@ManyToOne(optional = false)
    @JoinColumn(name = "CLIENTE_ID_CLIENTE", nullable = false)
    private Cliente cliente;

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
