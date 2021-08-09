package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.domain.dto.NovaCompraNFDTO;
import br.com.zup.mercadolivre.domain.dto.RankingNovaCompraDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ComunicacaoSistemasController {

    @PostMapping(value = "/notas-fiscais")
    public void criaNota(@Valid @RequestBody NovaCompraNFDTO request) throws InterruptedException {
        System.out.println("criando nota "+request.toString());
    }

    @PostMapping(value = "/ranking")
    public void ranking(@Valid @RequestBody RankingNovaCompraDTO request) throws InterruptedException {
        System.out.println("criando ranking "+request.toString());
    }

}
