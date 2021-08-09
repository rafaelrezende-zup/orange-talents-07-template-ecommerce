package br.com.zup.mercadolivre.domain.dto;

import br.com.zup.mercadolivre.domain.Compra;
import br.com.zup.mercadolivre.domain.Pagamento;
import br.com.zup.mercadolivre.domain.enumeration.StatusPagamento;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PagamentoPagseguroDTO implements GatewayPagamentoDTO {

    @NotBlank
    private String idPagamento;

    @NotNull
    private StatusPagamento status;

    @Override
    public Pagamento toPagamento(Compra compra) {
        return new Pagamento(status, idPagamento, compra);
    }

    public String getIdPagamento() {
        return idPagamento;
    }

    public StatusPagamento getStatus() {
        return status;
    }
}
