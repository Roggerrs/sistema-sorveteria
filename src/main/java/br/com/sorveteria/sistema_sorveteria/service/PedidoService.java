package br.com.sorveteria.sistema_sorveteria.service;

import br.com.sorveteria.sistema_sorveteria.domain.dto.*;
import br.com.sorveteria.sistema_sorveteria.domain.entity.*;
import br.com.sorveteria.sistema_sorveteria.exception.BusinessException;
import br.com.sorveteria.sistema_sorveteria.exception.ResourceNotFoundException;
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
                .orElseThrow(() -> new ResourceNotFoundException("Atendente não encontrado"));

        Pedido pedido = new Pedido();
        pedido.setAtendente(atendente);
        pedido.setDataPedido(LocalDateTime.now());

        pedido = pedidoRepository.save(pedido);

        BigDecimal totalPedido = BigDecimal.ZERO;

        for (SorveteRequestDTO s : dto.getSorvetes()) {

            Tamanho tamanho = tamanhoRepository.findById(s.getTamanhoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Tamanho inválido"));

            List<Sabor> sabores = saborRepository.findAllById(s.getSabores());

            if (sabores.isEmpty()) {
                throw new BusinessException("Sabores inválidos");
            }

            int qtdSabores = sabores.size();

            if (tamanho.getDescricao().equalsIgnoreCase("PEQUENO") && qtdSabores > 2) {
                throw new BusinessException("Sorvete pequeno permite no máximo 2 sabores");
            }

            if (tamanho.getDescricao().equalsIgnoreCase("GRANDE") && qtdSabores > 3) {
                throw new BusinessException("Sorvete grande permite no máximo 3 sabores");
            }

            Sorvete sorvete = new Sorvete();
            sorvete.setPedido(pedido);
            sorvete.setTamanho(tamanho);
            sorvete.setSabores(sabores);
            sorveteRepository.save(sorvete);

            BigDecimal precoTamanho = tamanho.getPrecoTamanho();
            BigDecimal precoSabores = sabores.stream()
                    .map(sb -> sb.getPrecoAdicional() != null
                            ? sb.getPrecoAdicional()
                            : BigDecimal.ZERO)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            totalPedido = totalPedido.add(precoTamanho.add(precoSabores));
        }

        return new PedidoResponseDTO(
                pedido.getId(),
                atendente.getNome(),
                totalPedido
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
                        p.getAtendente().getNome(),
                        BigDecimal.ZERO
                ))
                .toList();
    }

    // =========================
    // GET /pedidos/{id}
    // =========================
    public PedidoDetalheResponseDTO buscarPorId(Long id) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        PedidoDetalheResponseDTO dto = new PedidoDetalheResponseDTO();
        dto.setId(pedido.getId());
        dto.setAtendente(pedido.getAtendente().getNome());
        dto.setDataPedido(pedido.getDataPedido());

        BigDecimal totalPedido = BigDecimal.ZERO;

        List<SorveteDetalheDTO> sorvetesDTO = pedido.getSorvetes().stream().map(s -> {

            BigDecimal precoTamanho = s.getTamanho().getPrecoTamanho();
            BigDecimal precoSabores = s.getSabores().stream()
                    .map(sb -> sb.getPrecoAdicional() != null
                            ? sb.getPrecoAdicional()
                            : BigDecimal.ZERO)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalSorvete = precoTamanho.add(precoSabores);

            SorveteDetalheDTO sd = new SorveteDetalheDTO();
            sd.setTamanho(s.getTamanho().getDescricao());
            sd.setPrecoTamanho(precoTamanho);
            sd.setSabores(s.getSabores().stream().map(Sabor::getNome).toList());
            sd.setPrecoSabores(precoSabores);
            sd.setPrecoTotal(totalSorvete);

            return sd;
        }).toList();

        for (SorveteDetalheDTO sd : sorvetesDTO) {
            totalPedido = totalPedido.add(sd.getPrecoTotal());
        }

        dto.setSorvetes(sorvetesDTO);
        dto.setTotal(totalPedido);

        return dto;
    }

    // =========================
    // PUT /pedidos/{id}/inativar
    // =========================
    @Transactional
    public void inativarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
        pedido.setAtivo(false);
    }
}
