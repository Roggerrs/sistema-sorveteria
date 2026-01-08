package br.com.sorveteria.sistema_sorveteria.controller;

import br.com.sorveteria.sistema_sorveteria.Jparepository.PedidoRepository;
import br.com.sorveteria.sistema_sorveteria.domain.entity.Pedido;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

private final PedidoRepository pedidoRepository;

public PedidoController(PedidoRepository pedidoRepository) {
    this.pedidoRepository = pedidoRepository;
}

//GET /PEDIDOS
    @GetMapping
    public List<Pedido> listar() {
    return pedidoRepository.findAll();

    }

}

