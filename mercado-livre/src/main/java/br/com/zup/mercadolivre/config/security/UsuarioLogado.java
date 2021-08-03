package br.com.zup.mercadolivre.config.security;

import br.com.zup.mercadolivre.domain.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

public class UsuarioLogado implements UserDetails {

    private Usuario usuario;
    private User user;

    public UsuarioLogado(@NotNull @Valid Usuario usuario, Collection<? extends GrantedAuthority> permissoes) {
        this.usuario = usuario;
        user = new User(usuario.getLogin(), usuario.getSenha(), permissoes);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
