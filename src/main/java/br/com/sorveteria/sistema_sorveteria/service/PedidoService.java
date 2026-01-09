package br.com.sorveteria.sistema_sorveteria.service;

import br.com.sorveteria.sistema_sorveteria.domain.dto.*;
import br.com.sorveteria.sistema_sorveteria.domain.entity.*;
import br.com.sorveteria.sistema_sorveteria.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

    // =========================
    // POST /pedidos
    // =========================
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

            if (sabores.isEmpty()) {
                throw new RuntimeException("Sabores inválidos");
            }

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

    // =========================
    // GET /pedidos
    // =========================
    public List<PedidoResponseDTO> listarTodos() {
        return pedidoRepository.findAll()
                .stream()
                .map(p -> new PedidoResponseDTO(
                        p.getId(),
                        p.getAtendente().getNome()
                ))
                .toList();
    }

    // =========================
    // GET /pedidos/{id}
    // =========================
    public PedidoDetalheResponseDTO buscarPorId(Long id) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        PedidoDetalheResponseDTO dto = new PedidoDetalheResponseDTO();
        dto.setId(pedido.getId());
        dto.setAtendente(pedido.getAtendente().getNome());
        dto.setDataPedido(pedido.getDataPedido());

        BigDecimal totalPedido = BigDecimal.ZERO;

        List<SorveteDetalheDTO> sorvetesDTO =
                pedido.getSorvetes().stream().map(s -> {

                    BigDecimal precoTamanho = s.getTamanho().getPrecoTamanho();

                    BigDecimal precoSabores = s.getSabores().stream()
                            .map(sb -> sb.getPrecoAdicional() != null
                                    ? sb.getPrecoAdicional()
                                    : BigDecimal.ZERO)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    SorveteDetalheDTO sd = new SorveteDetalheDTO();
                    // tamanho
                    sd.setTamanho(s.getTamanho().getDescricao());
                    precoTamanho = s.getTamanho().getPrecoTamanho();
                    sd.setPrecoTamanho(precoTamanho);
                    sd.setSabores(
                            s.getSabores().stream()
                                    .map(Sabor::getNome)
                                    .toList()
                    );
                    sd.setPrecoSabores(precoSabores);
                    sd.setPrecoTotal(precoTamanho.add(precoSabores));

                    return sd;
                }).toList();

        dto.setSorvetes(sorvetesDTO);

        return dto;
    }
}
