package br.com.zup.mercadolivre.domain.dto;

import br.com.zup.mercadolivre.domain.Caracteristica;
import br.com.zup.mercadolivre.domain.Produto;

import javax.validation.constraints.NotBlank;

public class NovaCaracteristicaDTO {

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Caracteristica toModel(Produto produto) {
        return new Caracteristica(this.nome, this.descricao, produto);
    }

}
