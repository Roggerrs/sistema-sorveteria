package br.com.sorveteria.sistema_sorveteria.domain.projection;

public interface TamanhosMaisVendidosProjection {
    String getDescricao();
    Long getTotal();
}


//SELECT t.descricao AS descricao, COUNT(*) AS total
//FROM Sorvete s
//JOIN s.tamanho t
//GROUP BY t.descricao
