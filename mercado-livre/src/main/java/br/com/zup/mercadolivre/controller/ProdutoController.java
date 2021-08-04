package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.config.security.UsuarioLogado;
import br.com.zup.mercadolivre.domain.Caracteristica;
import br.com.zup.mercadolivre.domain.Categoria;
import br.com.zup.mercadolivre.domain.Produto;
import br.com.zup.mercadolivre.domain.Usuario;
import br.com.zup.mercadolivre.domain.dto.NovaCategoriaDTO;
import br.com.zup.mercadolivre.domain.dto.NovoProdutoDTO;
import br.com.zup.mercadolivre.repository.CaracteristicaRepository;
import br.com.zup.mercadolivre.repository.CategoriaRepository;
import br.com.zup.mercadolivre.repository.ProdutoRepository;
import br.com.zup.mercadolivre.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public ProdutoController(ProdutoRepository produtoRepository,
                             CategoriaRepository categoriaRepository,
                             UsuarioRepository usuarioRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cria(@RequestBody @Valid NovoProdutoDTO dto, @AuthenticationPrincipal UsuarioLogado usuarioLogado) {
        Categoria categoria = categoriaRepository.getById(dto.getIdCategoria());
        Optional<Usuario> usuario = usuarioRepository.findByLogin(usuarioLogado.getUsername());
        if (usuario.isPresent()) {
            Produto produto = new Produto(dto, categoria, usuario.get());
            produtoRepository.save(produto);
            return ResponseEntity.ok().build();
        }
        throw new UsernameNotFoundException("Não foi possível encontrar usuário com email: " + usuarioLogado.getUsername());
    }

}
