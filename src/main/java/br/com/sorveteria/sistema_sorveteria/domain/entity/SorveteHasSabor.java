package br.com.sorveteria.sistema_sorveteria.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "SORVETE_HAS_SABOR")
public class SorveteHasSabor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SORVETE_HAS_SABOR")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "SORVETE_ID_SORVETE", nullable = false)
    private Sorvete sorvete;

    @ManyToOne
    @JoinColumn(name = "SABOR_ID_SABOR", nullable = false)
    private Sabor sabor;

    // ========= GETTERS / SETTERS =========

    public Long getId() {
        return id;
    }

    public Sorvete getSorvete() {
        return sorvete;
    }

    public void setSorvete(Sorvete sorvete) {
        this.sorvete = sorvete;
    }

    public Sabor getSabor() {
        return sabor;
    }

    public void setSabor(Sabor sabor) {
        this.sabor = sabor;
    }
}
