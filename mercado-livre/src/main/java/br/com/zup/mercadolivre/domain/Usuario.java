package br.com.zup.mercadolivre.domain;

import br.com.zup.mercadolivre.domain.dto.NovoUsuarioDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    @Email
    private String login;

    @NotBlank
    @Size(min = 6)
    private String senha;

    private LocalDateTime dataRegistro;

    public Usuario(NovoUsuarioDTO dto) {
        this.login = dto.getLogin();
        this.senha = new BCryptPasswordEncoder().encode(dto.getSenha());
        this.dataRegistro = LocalDateTime.now();
    }

    @Deprecated
    public Usuario() {
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }
}
