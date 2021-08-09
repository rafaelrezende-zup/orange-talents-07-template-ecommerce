package br.com.zup.mercadolivre.domain.dto;

import javax.validation.constraints.NotNull;

public class RankingNovaCompraDTO {

    @NotNull
    private final Long idCompra;

    @NotNull
    private final Long idVendedor;

    public RankingNovaCompraDTO(Long idCompra, Long idVendedor) {
        this.idCompra = idCompra;
        this.idVendedor = idVendedor;
    }

    @Override
    public String toString() {
        return "RankingNovaCompraDTO{" +
                "idCompra=" + idCompra +
                ", idVendedor=" + idVendedor +
                '}';
    }

    public Long getIdCompra() {
        return idCompra;
    }

    public Long getIdVendedor() {
        return idVendedor;
    }
}
