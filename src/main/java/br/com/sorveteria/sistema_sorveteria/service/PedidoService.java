package br.com.sorveteria.sistema_sorveteria.service;

import br.com.sorveteria.sistema_sorveteria.domain.dto.PedidoRequestDTO;
import br.com.sorveteria.sistema_sorveteria.domain.dto.PedidoResponseDTO;
import br.com.sorveteria.sistema_sorveteria.domain.dto.SorveteRequestDTO;
import br.com.sorveteria.sistema_sorveteria.domain.entity.*;
import br.com.sorveteria.sistema_sorveteria.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final AtendenteRepository atendenteRepository;
    private final TamanhoRepository tamanhoRepository;
    private final SaborRepository saborRepository;
    private final SorveteRepository sorveteRepository;

    public PedidoService(
            PedidoRepository pedidoRepository,
            AtendenteRepository atendenteRepository,
            TamanhoRepository tamanhoRepository,
            SaborRepository saborRepository,
            SorveteRepository sorveteRepository
    ) {
        this.pedidoRepository = pedidoRepository;
        this.atendenteRepository = atendenteRepository;
        this.tamanhoRepository = tamanhoRepository;
        this.saborRepository = saborRepository;
        this.sorveteRepository = sorveteRepository;
    }

    @Transactional
    public PedidoResponseDTO criarPedido(PedidoRequestDTO dto) {

        Atendente atendente = atendenteRepository.findById(dto.getAtendenteId())
                .orElseThrow(() -> new RuntimeException("Atendente não encontrado"));

        Pedido pedido = new Pedido();
        pedido.setAtendente(atendente);
        pedido.setDataPedido(LocalDateTime.now());

        pedido = pedidoRepository.save(pedido);

        for (SorveteRequestDTO s : dto.getSorvetes()) {

            Tamanho tamanho = tamanhoRepository.findById(s.getTamanhoId())
                    .orElseThrow(() -> new RuntimeException("Tamanho inválido"));

            List<Sabor> sabores = saborRepository.findAllById(s.getSaboresIds());

            Sorvete sorvete = new Sorvete();
            sorvete.setPedido(pedido);
            sorvete.setTamanho(tamanho);
            sorvete.setSabores(sabores);

            sorveteRepository.save(sorvete);
        }

        return new PedidoResponseDTO(
                pedido.getId(),
                atendente.getNome()
        );
    }

    public List<PedidoResponseDTO> listarTodos() {
        return pedidoRepository.findAll()
                .stream()
                .map(p -> new PedidoResponseDTO(
                        p.getId(),
                        p.getAtendente().getNome()
                ))
                .collect(Collectors.toList());
    }
}
