package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.domain.Usuario;
import br.com.zup.mercadolivre.domain.dto.NovoUsuarioDTO;
import br.com.zup.mercadolivre.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioRepository repository;

    public UsuarioController(UsuarioRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cria(@RequestBody @Valid NovoUsuarioDTO dto) {
        Usuario usuario = new Usuario(dto);
        repository.save(usuario);
        return ResponseEntity.ok().build();
    }

}
