package br.com.sorveteria.sistema_sorveteria.repository;

import br.com.sorveteria.sistema_sorveteria.domain.entity.Atendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtendenteRepository extends JpaRepository<Atendente, Long> {
}
