package br.com.sorveteria.sistema_sorveteria.repository;

import br.com.sorveteria.sistema_sorveteria.domain.entity.SorveteHasSabor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SorveteHasSaborRepository
        extends JpaRepository<SorveteHasSabor, Long> {
}
