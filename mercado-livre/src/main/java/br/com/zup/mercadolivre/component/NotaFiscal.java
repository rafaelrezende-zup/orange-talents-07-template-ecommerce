package br.com.zup.mercadolivre.component;

import br.com.zup.mercadolivre.domain.Compra;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class NotaFiscal implements CompraSucesso {
    public void processa(Compra compra) {
        Assert.isTrue(compra.processadaComSucesso(), "A compra não foi concluída com sucesso.");
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> request = Map.of("idCompra", compra.getId(),
                "idComprador", compra.getUsuario().getId());

        restTemplate.postForEntity("http://localhost:8080/notas-fiscais",
                request, String.class);
    }

}
