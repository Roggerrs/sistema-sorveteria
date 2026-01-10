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

    @Transactional
    public PedidoResponseDTO criarPedido(PedidoRequestDTO dto) {

        Atendente atendente = atendenteRepository.findById(dto.getAtendenteId())
                .orElseThrow(() -> new ResourceNotFoundException("Atendente não encontrado"));

        Pedido pedido = new Pedido();
        pedido.setAtendente(atendente);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setAtivo(true);

        pedido = pedidoRepository.save(pedido);

        BigDecimal totalPedido = BigDecimal.ZERO;

        for (SorveteRequestDTO s : dto.getSorvetes()) {

            Tamanho tamanho = tamanhoRepository.findById(s.getTamanhoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Tamanho inválido"));

            List<Sabor> sabores = saborRepository.findAllById(s.getSaboresIds());

            if (sabores.isEmpty()) {
                throw new BusinessException("Selecione ao menos um sabor");
            }

            if ("PEQUENO".equalsIgnoreCase(tamanho.getDescricao()) && sabores.size() > 2) {
                throw new BusinessException("Pequeno permite até 2 sabores");
            }

            if ("GRANDE".equalsIgnoreCase(tamanho.getDescricao()) && sabores.size() > 3) {
                throw new BusinessException("Grande permite até 3 sabores");
            }

            Sorvete sorvete = new Sorvete();
            sorvete.setPedido(pedido);
            sorvete.setTamanho(tamanho);
            sorvete.setSabores(sabores);
            sorvete.setAtivo(true);

            sorveteRepository.save(sorvete);

            BigDecimal precoTamanho = tamanho.getPrecoTamanho();
            BigDecimal precoSabores = sabores.stream()
                    .map(sb -> sb.getPrecoAdicional() == null ? BigDecimal.ZERO : sb.getPrecoAdicional())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            totalPedido = totalPedido.add(precoTamanho.add(precoSabores));
        }

        return new PedidoResponseDTO(
                pedido.getId(),
                atendente.getNome(),
                totalPedido
        );
    }

    public List<PedidoResponseDTO> listarTodos() {
        return pedidoRepository.findAll().stream().map(p -> {

            BigDecimal total = p.getSorvetes().stream().map(s -> {
                BigDecimal t = s.getTamanho().getPrecoTamanho();
                BigDecimal sab = s.getSabores().stream()
                        .map(x -> x.getPrecoAdicional() == null ? BigDecimal.ZERO : x.getPrecoAdicional())
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return t.add(sab);
            }).reduce(BigDecimal.ZERO, BigDecimal::add);

            return new PedidoResponseDTO(p.getId(), p.getAtendente().getNome(), total);
        }).toList();
    }

    public PedidoDetalheResponseDTO buscarPorId(Long id) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        BigDecimal total = BigDecimal.ZERO;

        List<SorveteDetalheDTO> sorvetes = pedido.getSorvetes().stream().map(s -> {

            BigDecimal t = s.getTamanho().getPrecoTamanho();
            BigDecimal sab = s.getSabores().stream()
                    .map(x -> x.getPrecoAdicional() == null ? BigDecimal.ZERO : x.getPrecoAdicional())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal subtotal = t.add(sab);

            SorveteDetalheDTO dto = new SorveteDetalheDTO();
            dto.setTamanho(s.getTamanho().getDescricao());
            dto.setSabores(s.getSabores().stream().map(Sabor::getNome).toList());
            dto.setPrecoTotal(subtotal);

            return dto;
        }).toList();

        for (SorveteDetalheDTO s : sorvetes) {
            total = total.add(s.getPrecoTotal());
        }

        PedidoDetalheResponseDTO r = new PedidoDetalheResponseDTO();
        r.setId(pedido.getId());
        r.setAtendente(pedido.getAtendente().getNome());
        r.setDataPedido(pedido.getDataPedido());
        r.setSorvetes(sorvetes);
        r.setTotal(total);

        return r;
    }

    @Transactional
    public void inativarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
        pedido.setAtivo(false);
    }
}
