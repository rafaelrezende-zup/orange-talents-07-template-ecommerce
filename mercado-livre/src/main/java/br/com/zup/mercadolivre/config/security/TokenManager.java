package br.com.zup.mercadolivre.config.security;

import br.com.zup.mercadolivre.domain.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenManager {

    @Value("${forum.jwt.expiration}")
    private String expiration;

    @Value("${forum.jwt.secret}")
    private String secret;

    public String geraToken(Authentication authentication) {

        UsuarioLogado usuarioLogado = (UsuarioLogado) authentication.getPrincipal();
        Date date = new Date();
        Date expiracao = new Date(date.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("desafio_mercado_livre_ot7")
                .setSubject(usuarioLogado.getUsername())
                .setIssuedAt(date)
                .setExpiration(expiracao)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Boolean isValid(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getNameUser(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

}
