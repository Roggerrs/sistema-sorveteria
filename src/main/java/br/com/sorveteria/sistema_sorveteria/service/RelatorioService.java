package br.com.sorveteria.sistema_sorveteria.service;

import br.com.sorveteria.sistema_sorveteria.domain.dto.*;
import br.com.sorveteria.sistema_sorveteria.repository.RelatorioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelatorioService {

    private final RelatorioRepository repository;

    public RelatorioService(RelatorioRepository repository) {
        this.repository = repository;
    }

    public TotalFaturadoDTO totalFaturado() {
        var p = repository.totalFaturado();

        if (p == null || p.getTotal() == null) {
            return new TotalFaturadoDTO(0.0);
        }

        return new TotalFaturadoDTO(p.getTotal());
    }

    public List<TotalPorAtendenteDTO> totalPorAtendente() {
        return repository.totalPorAtendente()
                .stream()
                .map(p -> new TotalPorAtendenteDTO(
                        p.getAtendente(),
                        p.getTotal() == null ? 0.0 : p.getTotal()
                ))
                .toList();
    }

    public List<SaboresMaisVendidosDTO> saboresMaisVendidos() {
        return repository.saboresMaisVendidos()
                .stream()
                .map(p -> new SaboresMaisVendidosDTO(p.getNome(), p.getTotal()))
                .toList();
    }

    public List<TamanhosMaisVendidosDTO> tamanhosMaisVendidos() {
        return repository.tamanhosMaisVendidos()
                .stream()
                .map(p -> new TamanhosMaisVendidosDTO(p.getDescricao(), p.getTotal()))
                .toList();
    }
}
