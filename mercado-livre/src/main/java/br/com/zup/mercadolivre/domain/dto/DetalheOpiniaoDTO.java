package br.com.zup.mercadolivre.domain.dto;

import br.com.zup.mercadolivre.domain.Opiniao;

public class DetalheOpiniaoDTO {

    private final Long id;
    private final Integer nota;
    private final String titulo;
    private final String descricao;
    private final DetalheUsuarioDTO usuario;

    public DetalheOpiniaoDTO(Opiniao opiniao) {
        this.id = opiniao.getId();
        this.nota = opiniao.getNota();
        this.titulo = opiniao.getTitulo();
        this.descricao = opiniao.getDescricao();
        this.usuario = new DetalheUsuarioDTO(opiniao.getUsuario());
    }

    public Long getId() {
        return id;
    }

    public Integer getNota() {
        return nota;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public DetalheUsuarioDTO getUsuario() {
        return usuario;
    }
}
