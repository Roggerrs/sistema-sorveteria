package br.com.sorveteria.sistema_sorveteria.controller;

import br.com.sorveteria.sistema_sorveteria.domain.dto.PedidoDetalheResponseDTO;
import br.com.sorveteria.sistema_sorveteria.domain.dto.PedidoRequestDTO;
import br.com.sorveteria.sistema_sorveteria.domain.dto.PedidoResponseDTO;
import br.com.sorveteria.sistema_sorveteria.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // POST /pedidos
    @PostMapping
    public PedidoResponseDTO criar(@RequestBody @Valid PedidoRequestDTO dto) {
        return pedidoService.criarPedido(dto);
    }

    // GET /pedidos
    @GetMapping
    public List<PedidoResponseDTO> listar() {
        return pedidoService.listarTodos();
    }

    // GET /pedidos/{id}
    @GetMapping("/{id}")
    public PedidoDetalheResponseDTO buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id);
    }

    // PUT /pedidos/{id}/inativar
    @PutMapping("/{id}/inativar")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        pedidoService.inativarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
