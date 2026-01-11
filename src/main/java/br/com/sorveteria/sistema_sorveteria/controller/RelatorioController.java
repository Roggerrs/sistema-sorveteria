package br.com.sorveteria.sistema_sorveteria.controller;

import br.com.sorveteria.sistema_sorveteria.domain.dto.*;
import br.com.sorveteria.sistema_sorveteria.service.RelatorioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/relatorios")
@CrossOrigin(origins = "*")
public class RelatorioController {

    private final RelatorioService service;

    public RelatorioController(RelatorioService service) {
        this.service = service;
    }

    @GetMapping("/total-faturado")
    public TotalFaturadoDTO totalFaturado() {
        return service.totalFaturado();
    }

    @GetMapping("/por-atendente")
    public List<TotalPorAtendenteDTO> totalPorAtendente() {
        return service.totalPorAtendente();
    }

    @GetMapping("/sabores-mais-vendidos")
    public List<SaboresMaisVendidosDTO> saboresMaisVendidos() {
        return service.saboresMaisVendidos();
    }

    @GetMapping("/tamanhos-mais-vendidos")
    public List<TamanhosMaisVendidosDTO> tamanhosMaisVendidos() {
        return service.tamanhosMaisVendidos();
    }
}
