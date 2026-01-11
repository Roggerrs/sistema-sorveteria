package br.com.sorveteria.sistema_sorveteria.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Entidade fake usada apenas para satisfazer o JpaRepository
 * Nenhuma tabela real depende disso
 */
@Entity
public class RelatorioFake {

    @Id
    private Long id;
}
