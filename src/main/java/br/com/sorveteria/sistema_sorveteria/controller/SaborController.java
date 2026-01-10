package br.com.sorveteria.sistema_sorveteria.controller;

import br.com.sorveteria.sistema_sorveteria.domain.entity.Sabor;
import br.com.sorveteria.sistema_sorveteria.repository.SaborRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sabores")
@CrossOrigin(origins = "*")
public class SaborController {

    private final SaborRepository saborRepository;

    public SaborController(SaborRepository saborRepository) {
        this.saborRepository = saborRepository;
    }

    @GetMapping
    public List<Sabor> listar() {
        return saborRepository.findAll();
    }
}
