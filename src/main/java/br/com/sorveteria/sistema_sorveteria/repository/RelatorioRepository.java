package br.com.sorveteria.sistema_sorveteria.repository;

import br.com.sorveteria.sistema_sorveteria.domain.entity.RelatorioFake;
import br.com.sorveteria.sistema_sorveteria.domain.projection.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelatorioRepository
        extends JpaRepository<RelatorioFake, Long> {

    // TOTAL FATURADO
    @Query(value = """
        SELECT
            SUM(t.PRECO_TAMANHO + COALESCE(s.PRECO_ADICIONAL, 0)) AS total
        FROM SORVETE so
        INNER JOIN TAMANHO t ON t.ID_TAMANHO = so.TAMANHO_ID_TAMANHO
        LEFT JOIN SORVETE_HAS_SABOR shs ON shs.SORVETE_ID_SORVETE = so.ID_SORVETE
        LEFT JOIN SABOR s ON s.ID_SABOR = shs.SABOR_ID_SABOR
        """, nativeQuery = true)
    TotalFaturadoProjection totalFaturado();

    // TOTAL POR ATENDENTE
    @Query(value = """
        SELECT
            a.NOME AS atendente,
            SUM(t.PRECO_TAMANHO + COALESCE(s.PRECO_ADICIONAL, 0)) AS total
        FROM PEDIDO p
        INNER JOIN ATENDENTE a ON a.ID_ATENDENTE = p.ATENDENTE_ID_ATENDENTE
        INNER JOIN SORVETE so ON so.PEDIDO_ID_PEDIDO = p.ID_PEDIDO
        INNER JOIN TAMANHO t ON t.ID_TAMANHO = so.TAMANHO_ID_TAMANHO
        LEFT JOIN SORVETE_HAS_SABOR shs ON shs.SORVETE_ID_SORVETE = so.ID_SORVETE
        LEFT JOIN SABOR s ON s.ID_SABOR = shs.SABOR_ID_SABOR
        GROUP BY a.NOME
        """, nativeQuery = true)
    List<TotalPorAtendenteProjection> totalPorAtendente();

    // SABORES MAIS VENDIDOS
    @Query(value = """
        SELECT
            s.NOME AS nome,
            COUNT(*) AS total
        FROM SORVETE_HAS_SABOR shs
        JOIN SABOR s ON s.ID_SABOR = shs.SABOR_ID_SABOR
        GROUP BY s.NOME
        ORDER BY total DESC
        """, nativeQuery = true)
    List<SaboresMaisVendidosProjection> saboresMaisVendidos();

    // TAMANHOS MAIS VENDIDOS
    @Query(value = """
        SELECT
            t.DESCRICAO AS descricao,
            COUNT(*) AS total
        FROM SORVETE so
        JOIN TAMANHO t ON t.ID_TAMANHO = so.TAMANHO_ID_TAMANHO
        GROUP BY t.DESCRICAO
        """, nativeQuery = true)
    List<TamanhosMaisVendidosProjection> tamanhosMaisVendidos();
}
