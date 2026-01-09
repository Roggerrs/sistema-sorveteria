package br.com.sorveteria.sistema_sorveteria.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "SORVETE")
public class Sorvete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SORVETE")
    private Long id;

    // 🔴 EVITA LOOP DE SERIALIZAÇÃO (Swagger / JSON)
    @ManyToOne
    @JoinColumn(name = "PEDIDO_ID_PEDIDO", nullable = false)
    @JsonIgnore
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "TAMANHO_ID_TAMANHO", nullable = false)
    private Tamanho tamanho;

    @ManyToMany
    @JoinTable(
            name = "SORVETE_has_SABOR",
            joinColumns = @JoinColumn(name = "SORVETE_ID_SORVETE"),
            inverseJoinColumns = @JoinColumn(name = "SABOR_ID_SABOR")
    )
    private List<Sabor> sabores;

    @Column(name = "ATIVO")
    private Boolean ativo = true;

    // ======================
    // GETTERS E SETTERS
    // ======================

    public Long getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Tamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }

    public List<Sabor> getSabores() {
        return sabores;
    }

    public void setSabores(List<Sabor> sabores) {
        this.sabores = sabores;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
