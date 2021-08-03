package br.com.zup.mercadolivre.domain.dto;

import br.com.zup.mercadolivre.domain.Usuario;
import br.com.zup.mercadolivre.validator.UniqueValue;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NovoUsuarioDTO {

    @NotBlank
    @UniqueValue(domainClass = Usuario.class, fieldName = "login", message = "Usuário com este login já cadastrado.")
    @Email
    private String login;

    @NotBlank
    @Size(min = 6)
    private String senha;

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }
}
