package br.com.sorveteria.sistema_sorveteria.domain.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "SORVETE")

public class Sorvete {

@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SORVETE")
    private Long id;

@ManyToOne(optional = false)
    @JoinColumn(name = "PEDIDO_ID_PEDIDO", nullable = false)
    private Pedido pedido;

@ManyToOne(optional = false)
    @JoinColumn(name = "TAMANHO_ID_TAMANHO", nullable = false)
    private Tamanho tamanho;

@ManyToMany
    @JoinTable(
            name = "SORVETE_has_SABOR",
    joinColumns = @JoinColumn(name = "SORVETE_ID_SORVETE"),
    inverseJoinColumns = @JoinColumn(name = "SABOR_ID_SABOR")
    )

    private List<Sabor> sabores;

public Long getId() {
    return id;
}

public Pedido getPedido() {
    return pedido;
}

public Tamanho getTamanho() {
    return tamanho;
}

public List<Sabor> getSabores() {
return sabores;
}

public void setPedido(Pedido pedido) {
    this.pedido = pedido;
}

public void setTamanho(Tamanho tamanho) {
    this.tamanho = tamanho;
}

public void setSabores(List<Sabor> sabores) {
    this.sabores = sabores;
}

}
