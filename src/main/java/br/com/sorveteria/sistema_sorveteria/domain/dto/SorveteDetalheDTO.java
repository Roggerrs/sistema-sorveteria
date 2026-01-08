package br.com.sorveteria.sistema_sorveteria.domain.dto;

import java.util.List;

public class SorveteDetalheDTO {

    private String tamanho;
    private List<String> sabores;

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public List<String> getSabores() {
        return sabores;
    }

    public void setSabores(List<String> sabores) {
        this.sabores = sabores;
    }
}
