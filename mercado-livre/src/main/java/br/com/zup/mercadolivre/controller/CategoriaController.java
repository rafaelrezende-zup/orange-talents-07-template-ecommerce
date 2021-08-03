package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.domain.Categoria;
import br.com.zup.mercadolivre.domain.dto.NovaCategoriaDTO;
import br.com.zup.mercadolivre.repository.CategoriaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    private final CategoriaRepository repository;

    public CategoriaController(CategoriaRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cria(@RequestBody @Valid NovaCategoriaDTO dto) {
        Categoria categoria = new Categoria(dto);
        if (Objects.nonNull(dto.getIdCategoriaMae())) {
            Optional<Categoria> categoriaMae = repository.findById(dto.getIdCategoriaMae());
            categoriaMae.ifPresent(categoria::setCategoriaMae);
        }
        repository.save(categoria);
        return ResponseEntity.ok().build();
    }

}
