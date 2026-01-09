package br.com.sorveteria.sistema_sorveteria.controller;

import br.com.sorveteria.sistema_sorveteria.domain.dto.AtendenteRequestDTO;
import br.com.sorveteria.sistema_sorveteria.domain.dto.AtendenteResponseDTO;
import br.com.sorveteria.sistema_sorveteria.domain.entity.Atendente;
import br.com.sorveteria.sistema_sorveteria.repository.AtendenteRepository;
import br.com.sorveteria.sistema_sorveteria.service.AtendenteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atendentes")
public class AtendenteController {

    private final AtendenteService atendenteService;

    public AtendenteController(AtendenteService atendenteService) {
        this.atendenteService = atendenteService;
    }

    @PostMapping
    public AtendenteResponseDTO criar(@RequestBody @Valid AtendenteRequestDTO dto) {
        return atendenteService.criar(dto);
    }

    @GetMapping
    public List<AtendenteResponseDTO> listar() {
        return atendenteService.listar();
    }

    @PutMapping("/{id}/inativar")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        atendenteService.inativarAtendente(id);
        return ResponseEntity.noContent().build();
    }

}

