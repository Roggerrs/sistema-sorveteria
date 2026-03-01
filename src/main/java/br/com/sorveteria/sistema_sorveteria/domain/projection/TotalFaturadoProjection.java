package br.com.sorveteria.sistema_sorveteria.domain.projection;

public interface TotalFaturadoProjection {
    Double getTotal();
}
//@Query("SELECT SUM(p.valor) AS total FROM Pedido p")