package br.com.sorveteria.sistema_sorveteria.service;

import br.com.sorveteria.sistema_sorveteria.domain.dto.*;
import br.com.sorveteria.sistema_sorveteria.domain.entity.*;
import br.com.sorveteria.sistema_sorveteria.exception.BusinessException;
import br.com.sorveteria.sistema_sorveteria.exception.ResourceNotFoundException;
import br.com.sorveteria.sistema_sorveteria.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final AtendenteRepository atendenteRepository;
    private final TamanhoRepository tamanhoRepository;
    private final SaborRepository saborRepository;

    public PedidoService(
            PedidoRepository pedidoRepository,
            AtendenteRepository atendenteRepository,
            TamanhoRepository tamanhoRepository,
            SaborRepository saborRepository
    ) {
        this.pedidoRepository = pedidoRepository;
        this.atendenteRepository = atendenteRepository;
        this.tamanhoRepository = tamanhoRepository;
        this.saborRepository = saborRepository;
    }

    // =========================
    // CRIAR PEDIDO
    // =========================
    @Transactional
    public PedidoResponseDTO criarPedido(PedidoRequestDTO dto) {

        if (dto.getAtendenteId() == null) {
            throw new BusinessException("Atendente é obrigatório");
        }

        if (dto.getSorvetes() == null || dto.getSorvetes().isEmpty()) {
            throw new BusinessException("Pedido deve conter pelo menos um sorvete");
        }

        Atendente atendente = atendenteRepository.findById(dto.getAtendenteId())
                .orElseThrow(() -> new BusinessException("Atendente não encontrado"));

        Pedido pedido = new Pedido(); // 👈 DATA JÁ CORRETA NO CONSTRUTOR
        pedido.setAtendente(atendente);
        pedido.setAtivo(true);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        for (SorveteRequestDTO sorveteDTO : dto.getSorvetes()) {

            Tamanho tamanho = tamanhoRepository.findById(sorveteDTO.getTamanhoId())
                    .orElseThrow(() -> new BusinessException("Tamanho não encontrado"));

            List<Sabor> sabores = saborRepository.findAllById(sorveteDTO.getSaboresIds());

            if (sabores.isEmpty()) {
                throw new BusinessException("Sabores inválidos");
            }

            Sorvete sorvete = new Sorvete();
            sorvete.setPedido(pedidoSalvo);
            sorvete.setTamanho(tamanho);
            sorvete.setSabores(sabores);

            pedidoSalvo.getSorvetes().add(sorvete);
        }

        pedidoRepository.save(pedidoSalvo);

        BigDecimal total = pedidoSalvo.getSorvetes().stream()
                .map(s -> {
                    BigDecimal precoTamanho = s.getTamanho().getPrecoTamanho();
                    BigDecimal precoSabores = s.getSabores().stream()
                            .map(Sabor::getPrecoAdicional)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    return precoTamanho.add(precoSabores);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new PedidoResponseDTO(
                pedidoSalvo.getId(),
                atendente.getNome(),
                total
        );
    }

    // =========================
    // BUSCAR DETALHES
    // =========================
    public PedidoDetalheResponseDTO buscarPorId(Long id) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        PedidoDetalheResponseDTO dto = new PedidoDetalheResponseDTO();
        dto.setId(pedido.getId());
        dto.setAtendente(pedido.getAtendente().getNome());
        dto.setDataPedido(pedido.getDataPedido()); // ✅ AGORA BATE

        // restante igual
        // ...
        return dto;
    }
}
