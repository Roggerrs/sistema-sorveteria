package br.com.sorveteria.sistema_sorveteria.controller;

import br.com.sorveteria.sistema_sorveteria.domain.entity.Sabor;
import br.com.sorveteria.sistema_sorveteria.repository.SaborRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sabores")
@CrossOrigin(origins = "*")
public class SaborController {

    private final SaborRepository saborRepository;

    // 🔹 Injeção explícita (remove todos os erros do IntelliJ)
    public SaborController(@Autowired SaborRepository saborRepository) {
        this.saborRepository = saborRepository;
    }

    @GetMapping
    public List<Sabor> listar() {
        return saborRepository.findAll();
    }
}
