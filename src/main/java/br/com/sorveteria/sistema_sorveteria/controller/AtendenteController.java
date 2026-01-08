package br.com.sorveteria.sistema_sorveteria.controller;

import br.com.sorveteria.sistema_sorveteria.domain.entity.Atendente;
import br.com.sorveteria.sistema_sorveteria.repository.AtendenteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atendentes")
public class AtendenteController {

    private final AtendenteRepository atendenteRepository;

    public AtendenteController(AtendenteRepository atendenteRepository) {
        this.atendenteRepository = atendenteRepository;
    }

    // GET /atendentes
    @GetMapping
    public List<Atendente> listar() {
        return atendenteRepository.findAll();
    }

    // POST /atendentes
    @PostMapping
    public Atendente criar(@RequestBody Atendente atendente) {
        return atendenteRepository.save(atendente);
    }
}
