package br.com.sorveteria.sistema_sorveteria.service;

import br.com.sorveteria.sistema_sorveteria.domain.dto.AtendenteRequestDTO;
import br.com.sorveteria.sistema_sorveteria.domain.dto.AtendenteResponseDTO;
import br.com.sorveteria.sistema_sorveteria.domain.entity.Atendente;
import br.com.sorveteria.sistema_sorveteria.repository.AtendenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtendenteService {

    private final AtendenteRepository atendenteRepository;

    public AtendenteService(AtendenteRepository atendenteRepository) {
        this.atendenteRepository = atendenteRepository;
    }

    public AtendenteResponseDTO criar(AtendenteRequestDTO dto) {

        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new RuntimeException("Nome do atendente é obrigatório");
        }

        Atendente atendente = new Atendente();
        atendente.setNome(dto.getNome());

        atendente = atendenteRepository.save(atendente);

        return new AtendenteResponseDTO(
                atendente.getId(),
                atendente.getNome()
        );
    }

    public List<AtendenteResponseDTO> listar() {
        return atendenteRepository.findAll()
                .stream()
                .map(a -> new AtendenteResponseDTO(
                        a.getId(),
                        a.getNome()
                ))
                .toList();


    }

    @Transactional
    public void inativarAtendente(Long id) {
        Atendente atendente = atendenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atendente não encontrado"));

        atendente.setAtivo(false);
    }


}

