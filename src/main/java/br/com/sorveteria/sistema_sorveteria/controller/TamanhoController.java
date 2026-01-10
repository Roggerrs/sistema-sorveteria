package br.com.sorveteria.sistema_sorveteria.controller;

import br.com.sorveteria.sistema_sorveteria.domain.entity.Tamanho;
import br.com.sorveteria.sistema_sorveteria.repository.TamanhoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tamanhos")
@CrossOrigin(origins = "*")
public class TamanhoController {

    private final TamanhoRepository tamanhoRepository;

    public TamanhoController(TamanhoRepository tamanhoRepository) {
        this.tamanhoRepository = tamanhoRepository;
    }

    @GetMapping
    public List<Tamanho> listar() {
        return tamanhoRepository.findAll();
    }
}
