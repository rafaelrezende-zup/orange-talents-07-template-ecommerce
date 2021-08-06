package br.com.zup.mercadolivre.domain.dto;

import br.com.zup.mercadolivre.domain.Pergunta;

import java.time.LocalDateTime;

public class DetalhePerguntaDTO {

    private final Long id;
    private final String titulo;
    private final LocalDateTime dataRegistro;
    private final DetalheUsuarioDTO usuario;

    public DetalhePerguntaDTO(Pergunta pergunta) {
        this.id = pergunta.getId();
        this.titulo = pergunta.getTitulo();
        this.dataRegistro = pergunta.getDataRegistro();
        this.usuario = new DetalheUsuarioDTO(pergunta.getUsuario());
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public DetalheUsuarioDTO getUsuario() {
        return usuario;
    }
}
