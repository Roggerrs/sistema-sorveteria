package br.com.sorveteria.sistema_sorveteria.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class PedidoRequestDTO {

    @NotNull
    private Long atendenteId;

    @NotEmpty
    private List<SorveteRequestDTO> sorvetes;

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
