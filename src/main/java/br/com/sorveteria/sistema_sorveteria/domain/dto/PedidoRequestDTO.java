package br.com.sorveteria.sistema_sorveteria.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class PedidoRequestDTO {

    @NotNull(message = "Atendente é obrigatório")
    private Long atendenteId;

    @NotEmpty(message = "Pedido deve conter pelo menos um sorvete")
    private List<SorveteRequestDTO> sorvetes;

    public PedidoRequestDTO() {
    }

    public Long getAtendenteId() {
        return atendenteId;
    }

    public void setAtendenteId(Long atendenteId) {
        this.atendenteId = atendenteId;
    }

    public List<SorveteRequestDTO> getSorvetes() {
        return sorvetes;
    }

    public void setSorvetes(List<SorveteRequestDTO> sorvetes) {
        this.sorvetes = sorvetes;
    }
}
