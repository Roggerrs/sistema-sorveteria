package br.com.sorveteria.sistema_sorveteria.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class SorveteRequestDTO {

    @NotNull
    private Long tamanhoId;

    @NotEmpty
    private List<Long> saboresIds;

    public Long getTamanhoId() {
        return tamanhoId;
    }

    public void setTamanhoId(Long tamanhoId) {
        this.tamanhoId = tamanhoId;
    }

    public List<Long> getSaboresIds() {
        return saboresIds;
    }

    public void setSaboresIds(List<Long> saboresIds) {
        this.saboresIds = saboresIds;
    }
}
