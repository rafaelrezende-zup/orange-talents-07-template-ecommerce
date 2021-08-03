package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.config.security.TokenManager;
import br.com.zup.mercadolivre.domain.dto.UserAuthenticationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class UserAuthenticationController {

    private final TokenManager tokenManager;
    private final AuthenticationManager manager;

    public UserAuthenticationController(TokenManager tokenManager, AuthenticationManager manager) {
        this.tokenManager = tokenManager;
        this.manager = manager;
    }

    @PostMapping
    public ResponseEntity<?> autenticar(@RequestBody @Valid UserAuthenticationDTO dto) {
        UsernamePasswordAuthenticationToken authenticationToken = dto.build();
        try {
            Authentication authentication = manager.authenticate(authenticationToken);
            String token = tokenManager.geraToken(authentication);
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
