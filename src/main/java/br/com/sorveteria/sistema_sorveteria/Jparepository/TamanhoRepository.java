package br.com.sorveteria.sistema_sorveteria.Jparepository;

import br.com.sorveteria.sistema_sorveteria.domain.entity.Tamanho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TamanhoRepository extends JpaRepository<Tamanho, Long> {
}
