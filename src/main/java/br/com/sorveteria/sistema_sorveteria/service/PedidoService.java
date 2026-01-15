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

        Atendente atendente = atendenteRepository.findById(dto.getAtendenteId())
                .orElseThrow(() -> new BusinessException("Atendente não encontrado"));

        Pedido pedido = new Pedido();
        pedido.setAtendente(atendente);
        pedido.setAtivo(true);

        for (SorveteRequestDTO s : dto.getSorvetes()) {

            Tamanho tamanho = tamanhoRepository.findById(s.getTamanhoId())
                    .orElseThrow(() -> new BusinessException("Tamanho não encontrado"));

            List<Sabor> sabores = saborRepository.findAllById(s.getSaboresIds());

            Sorvete sorvete = new Sorvete();
            sorvete.setPedido(pedido);
            sorvete.setTamanho(tamanho);
            sorvete.setSabores(sabores);

            pedido.getSorvetes().add(sorvete);
        }

        pedidoRepository.save(pedido);

        BigDecimal total = pedido.getSorvetes().stream()
                .map(s -> s.getTamanho().getPrecoTamanho().add(
                        s.getSabores().stream()
                                .map(Sabor::getPrecoAdicional)
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                ))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new PedidoResponseDTO(
                pedido.getId(),
                atendente.getNome(),
                total
        );
    }

    // =========================
    // LISTAR
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
    // BUSCAR POR ID
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

        dto.setTotal(
                sorvetes.stream()
                        .map(SorveteDetalheDTO::getPrecoTotal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        return dto;
    }

    // =========================
    // INATIVAR
    // =========================
    @Transactional
    public void inativarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
        pedido.setAtivo(false);
    }
}
