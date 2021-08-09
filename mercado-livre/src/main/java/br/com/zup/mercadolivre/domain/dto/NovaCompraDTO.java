package br.com.zup.mercadolivre.domain.dto;

import br.com.zup.mercadolivre.domain.Produto;
import br.com.zup.mercadolivre.domain.enumeration.GatewayPagamento;
import br.com.zup.mercadolivre.validator.Exist;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class NovaCompraDTO {

    @Positive
    private Integer quantidade;

    @Exist(domainClass = Produto.class)
    @NotNull
    private Long idProduto;

    @NotNull
    private GatewayPagamento gatewayPagamento;

    public Integer getQuantidade() {
        return quantidade;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public GatewayPagamento getGatewayPagamento() {
        return gatewayPagamento;
    }
}
