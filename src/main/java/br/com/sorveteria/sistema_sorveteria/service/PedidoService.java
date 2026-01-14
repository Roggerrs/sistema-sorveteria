package br.com.sorveteria.sistema_sorveteria.service;

import br.com.sorveteria.sistema_sorveteria.domain.dto.PedidoRequestDTO;
import br.com.sorveteria.sistema_sorveteria.domain.dto.SorveteRequestDTO;
import br.com.sorveteria.sistema_sorveteria.domain.entity.*;
import br.com.sorveteria.sistema_sorveteria.exception.BusinessException;
import br.com.sorveteria.sistema_sorveteria.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Pedido criarPedido(PedidoRequestDTO dto) {

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

        return pedidoRepository.save(pedidoSalvo);
    }
}
