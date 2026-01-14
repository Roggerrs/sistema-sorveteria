package br.com.sorveteria.sistema_sorveteria.service;

import br.com.sorveteria.sistema_sorveteria.domain.dto.*;
import br.com.sorveteria.sistema_sorveteria.domain.entity.*;
import br.com.sorveteria.sistema_sorveteria.exception.BusinessException;
import br.com.sorveteria.sistema_sorveteria.exception.ResourceNotFoundException;
import br.com.sorveteria.sistema_sorveteria.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

        Pedido pedido = new Pedido();
        pedido.setAtendente(atendente);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setAtivo(true);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        for (SorveteRequestDTO sorveteDTO : dto.getSorvetes()) {

            if (sorveteDTO.getTamanhoId() == null) {
                throw new BusinessException("Tamanho é obrigatório");
            }

            if (sorveteDTO.getSaboresIds() == null || sorveteDTO.getSaboresIds().isEmpty()) {
                throw new BusinessException("Selecione pelo menos um sabor");
            }

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
    // LISTAR PEDIDOS
    // =========================
    public List<PedidoResponseDTO> listarTodos() {
        return pedidoRepository.findByAtivoTrue()
                .stream()
                .map(p -> new PedidoResponseDTO(
                        p.getId(),
                        p.getAtendente().getNome(),
                        BigDecimal.ZERO
                ))
                .toList();
    }

    // =========================
    // BUSCAR DETALHES DO PEDIDO (🔥 ERRO CORRIGIDO AQUI 🔥)
    // =========================
    public PedidoDetalheResponseDTO buscarPorId(Long id) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        PedidoDetalheResponseDTO dto = new PedidoDetalheResponseDTO();
        dto.setId(pedido.getId());
        dto.setAtendente(pedido.getAtendente().getNome());
        dto.setDataPedido(pedido.getDataPedido());

        List<SorveteDetalheDTO> sorvetes = pedido.getSorvetes().stream().map(s -> {

            SorveteDetalheDTO sd = new SorveteDetalheDTO();
            sd.setTamanho(s.getTamanho().getDescricao());
            sd.setPrecoTamanho(s.getTamanho().getPrecoTamanho());

            sd.setSabores(
                    s.getSabores().stream()
                            .map(Sabor::getNome)
                            .toList()
            );

            BigDecimal precoSabores = s.getSabores().stream()
                    .map(Sabor::getPrecoAdicional)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            sd.setPrecoSabores(precoSabores);
            sd.setPrecoTotal(sd.getPrecoTamanho().add(precoSabores));

            return sd;
        }).toList();

        dto.setSorvetes(sorvetes);

        BigDecimal total = sorvetes.stream()
                .map(SorveteDetalheDTO::getPrecoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        dto.setTotal(total);

        return dto;
    }

    // =========================
    // INATIVAR PEDIDO
    // =========================
    @Transactional
    public void inativarPedido(Long id) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        pedido.setAtivo(false);
    }
}
