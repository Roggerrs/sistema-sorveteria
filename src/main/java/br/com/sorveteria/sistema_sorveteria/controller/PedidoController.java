package br.com.sorveteria.sistema_sorveteria.controller;

import br.com.sorveteria.sistema_sorveteria.domain.dto.PedidoDetalheResponseDTO;
import br.com.sorveteria.sistema_sorveteria.domain.dto.PedidoRequestDTO;
import br.com.sorveteria.sistema_sorveteria.domain.dto.PedidoResponseDTO;
import br.com.sorveteria.sistema_sorveteria.domain.entity.Pedido;
import br.com.sorveteria.sistema_sorveteria.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public PedidoResponseDTO criar(@Valid @RequestBody PedidoRequestDTO dto) {
        return pedidoService.criarPedido(dto);
    }

    @GetMapping
    public List<PedidoResponseDTO> listar() {
        return pedidoService.listarTodos();
    }

    @GetMapping("/{id}")
    public PedidoDetalheResponseDTO buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id);
    }
}
