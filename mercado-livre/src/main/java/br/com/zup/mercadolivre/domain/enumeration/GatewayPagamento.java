package br.com.zup.mercadolivre.domain.enumeration;

import br.com.zup.mercadolivre.domain.Compra;
import org.springframework.web.util.UriComponentsBuilder;

public enum GatewayPagamento {

    PAYPAL {
        @Override
        public String getUrlPagamento(Compra compra, UriComponentsBuilder uriComponentsBuilder) {
            String uri = uriComponentsBuilder.path("paypal/{id}").buildAndExpand(compra.getId()).toString();
            return "paypal.com?buyerId="+compra.getId()+"&redirectUrl="+uri;
        }
    },
    PAGSEGURO {
        @Override
        public String getUrlPagamento(Compra compra, UriComponentsBuilder uriComponentsBuilder) {
            String uri = uriComponentsBuilder.path("pagseguro/{id}").buildAndExpand(compra.getId()).toString();
            return "pagseguro.com?returnId="+compra.getId()+"&redirectUrl="+uri;
        }
    };

    public abstract String getUrlPagamento(Compra compra, UriComponentsBuilder uriComponentsBuilder);

}
