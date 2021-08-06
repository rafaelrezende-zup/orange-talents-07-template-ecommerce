package br.com.zup.mercadolivre.domain.dto;

import br.com.zup.mercadolivre.domain.Caracteristica;

public class DetalheCaracteristicaDTO {

    private final Long id;
    private final String nome;
    private final String descricao;

    public DetalheCaracteristicaDTO(Caracteristica caracteristica) {
        this.id = caracteristica.getId();
        this.nome = caracteristica.getNome();
        this.descricao = caracteristica.getDescricao();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
