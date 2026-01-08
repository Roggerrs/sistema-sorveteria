package br.com.sorveteria.sistema_sorveteria.controller;

import br.com.sorveteria.sistema_sorveteria.Jparepository.ClienteRepository;
import br.com.sorveteria.sistema_sorveteria.domain.entity.Cliente;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")

public class ClienteController {

    private final ClienteRepository  clienteRepository ;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    //Get /clientes
    @GetMapping
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    //Post / clientes

    @PostMapping
    public Cliente criar(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);

    }
}
