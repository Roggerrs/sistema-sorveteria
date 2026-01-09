package br.com.sorveteria.sistema_sorveteria.domain.dto;

import java.math.BigDecimal;
import java.util.List;

public class SorveteDetalheDTO {

    private String tamanho;
    private BigDecimal precoTamanho;
    private List<String> sabores;
    private BigDecimal precoSabores;
    private BigDecimal precoTotal;

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public BigDecimal getPrecoTamanho() {
        return precoTamanho;
    }

    public void setPrecoTamanho(BigDecimal precoTamanho) {
        this.precoTamanho = precoTamanho;
    }

    public List<String> getSabores() {
        return sabores;
    }

    public void setSabores(List<String> sabores) {
        this.sabores = sabores;
    }

    public BigDecimal getPrecoSabores() {
        return precoSabores;
    }

    public void setPrecoSabores(BigDecimal precoSabores) {
        this.precoSabores = precoSabores;
    }

    public BigDecimal getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(BigDecimal precoTotal) {
        this.precoTotal = precoTotal;
    }
}
