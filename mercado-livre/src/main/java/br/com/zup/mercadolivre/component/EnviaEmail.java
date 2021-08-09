package br.com.zup.mercadolivre.component;

import br.com.zup.mercadolivre.domain.Compra;
import br.com.zup.mercadolivre.domain.Pergunta;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Primary
public class EnviaEmail {

    public void enviaPerguntaAoVendedor(Pergunta pergunta) {
        System.out.println("Olá " + pergunta.getProduto().getUsuario().getLogin() +
                ", o usuário " + pergunta.getUsuario().getLogin() +
                " realizou a seguinte pergunta no produto " + pergunta.getProduto().getNome() +
                ": \"" + pergunta.getTitulo() + "\" e está aguardando sua resposta!");
    }

    public void informaCompraAoVendedor(Compra compra) {
        System.out.println(" Olá " + compra.getProduto().getUsuario().getLogin() +
                ", o usuário " + compra.getUsuario().getLogin() +
                " realizou uma compra do produto: " + compra.getProduto().getNome() +
                " \n Valor atual do produto: " + compra.getValorNoMomento() +
                " \n Quantidade comprada: " + compra.getQuantidade() +
                " \n Preço total da compra: " + (compra.getValorNoMomento().multiply(new BigDecimal(compra.getQuantidade()))) +
                " \n Forma de pagamento: " + compra.getGatewayPagamento() +
                " \n A compra está com status " + compra.getStatus() +
                " \n Parabéns pela venda!");
    }

}
