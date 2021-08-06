package br.com.zup.mercadolivre.domain.dto;

import br.com.zup.mercadolivre.domain.Usuario;

import java.time.LocalDateTime;

public class DetalheUsuarioDTO {

    private final Long id;
    private final String login;
    private final LocalDateTime dataRegistro;

    public DetalheUsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.login = usuario.getLogin();
        this.dataRegistro = usuario.getDataRegistro();
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }
}
