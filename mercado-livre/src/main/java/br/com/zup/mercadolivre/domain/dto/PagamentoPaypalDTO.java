package br.com.zup.mercadolivre.domain.dto;

import br.com.zup.mercadolivre.domain.Compra;
import br.com.zup.mercadolivre.domain.Pagamento;
import br.com.zup.mercadolivre.domain.enumeration.StatusPagamento;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PagamentoPaypalDTO implements GatewayPagamentoDTO {

    @NotBlank
    private String idPagamento;

    @NotNull
    @Max(1)
    @Min(0)
    private int status;

    @Override
    public Pagamento toPagamento(Compra compra) {
        StatusPagamento statusPagamento = this.status == 0 ? StatusPagamento.ERRO : StatusPagamento.SUCESSO;
        return new  Pagamento(statusPagamento, idPagamento, compra);
    }
}
