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

    @ManyToOne
    @JoinColumn(name = "PEDIDO_ID_PEDIDO", nullable = false)
    @JsonIgnore
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "TAMANHO_ID_TAMANHO", nullable = false)
    private Tamanho tamanho;

    @OneToMany(mappedBy = "sorvete", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SorveteHasSabor> sabores;

    @Column(name = "ATIVO")
    private Boolean ativo = true;

    // ========= GETTERS / SETTERS =========

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

    public List<SorveteHasSabor> getSabores() {
        return sabores;
    }

    public void setSabores(List<SorveteHasSabor> sabores) {
        this.sabores = sabores;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
