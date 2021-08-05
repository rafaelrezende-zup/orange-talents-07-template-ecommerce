package br.com.zup.mercadolivre.component;

import br.com.zup.mercadolivre.domain.Pergunta;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class EnviaEmail {

    public void enviaPerguntaAoVendedor(Pergunta pergunta) {
        System.out.println("Olá " + pergunta.getProduto().getUsuario().getLogin() +
                ", o usuário " + pergunta.getUsuario().getLogin() +
                " realizou a seguinte pergunta no produto " + pergunta.getProduto().getNome() +
                ": \"" + pergunta.getTitulo() + "\" e está aguardando sua resposta!");
    }

}
