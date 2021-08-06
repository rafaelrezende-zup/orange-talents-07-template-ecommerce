package br.com.zup.mercadolivre.domain.dto;

import br.com.zup.mercadolivre.domain.ImagemProduto;

public class DetalheImagemProdutoDTO {

    private Long id;
    private String linkImagem;

    public DetalheImagemProdutoDTO(ImagemProduto imagem) {
        this.id = imagem.getId();
        this.linkImagem = imagem.getLink();
    }

    public Long getId() {
        return id;
    }

    public String getLinkImagem() {
        return linkImagem;
    }
}
