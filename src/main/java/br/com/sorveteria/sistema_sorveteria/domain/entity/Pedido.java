package br.com.sorveteria.sistema_sorveteria.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "ATIVO")
    private Boolean ativo = true;

    // ✅ CORREÇÃO CRÍTICA
    @OneToMany(
            mappedBy = "pedido",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Sorvete> sorvetes = new ArrayList<>();

    // =========================
    // GETTERS / SETTERS
    // =========================

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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public List<Sorvete> getSorvetes() {
        return sorvetes;
    }
}
