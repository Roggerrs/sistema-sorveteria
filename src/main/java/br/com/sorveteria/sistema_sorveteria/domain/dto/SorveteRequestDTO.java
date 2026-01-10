package br.com.sorveteria.sistema_sorveteria.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class SorveteRequestDTO {

    @NotNull
    private Long tamanhoId;

    @NotEmpty
    private List<Long> sabores;

    public Long getTamanhoId() {
        return tamanhoId;
    }

    public void setTamanhoId(Long tamanhoId) {
        this.tamanhoId = tamanhoId;
    }

    public List<Long> getSabores() {
        return sabores;
    }

    public void setSabores(List<Long> sabores) {
        this.sabores = sabores;
    }
}
