package br.com.zup.mercadolivre.domain.dto;

import br.com.zup.mercadolivre.domain.Compra;
import br.com.zup.mercadolivre.domain.Pagamento;

public interface GatewayPagamentoDTO {

    Pagamento toPagamento(Compra compra);

}
