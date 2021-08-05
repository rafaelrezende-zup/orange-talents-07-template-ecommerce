package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.component.Uploader;
import br.com.zup.mercadolivre.component.UploaderFake;
import br.com.zup.mercadolivre.config.security.UsuarioLogado;
import br.com.zup.mercadolivre.domain.Categoria;
import br.com.zup.mercadolivre.domain.Produto;
import br.com.zup.mercadolivre.domain.Usuario;
import br.com.zup.mercadolivre.domain.dto.NovaImagemDTO;
import br.com.zup.mercadolivre.domain.dto.NovoProdutoDTO;
import br.com.zup.mercadolivre.repository.CategoriaRepository;
import br.com.zup.mercadolivre.repository.ProdutoRepository;
import br.com.zup.mercadolivre.repository.UsuarioRepository;
import br.com.zup.mercadolivre.validator.ProibeCaracteristicaComNomeIgualValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;
    private final UploaderFake uploaderFake;
    private Uploader uploader;

    public ProdutoController(ProdutoRepository produtoRepository,
                             CategoriaRepository categoriaRepository,
                             UsuarioRepository usuarioRepository,
                             UploaderFake uploaderFake,
                             Uploader uploader) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
        this.uploaderFake = uploaderFake;
        this.uploader = uploader;
    }

    @InitBinder(value = "novoProdutoDTO")
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new ProibeCaracteristicaComNomeIgualValidator());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cria(@RequestBody @Valid NovoProdutoDTO dto, @AuthenticationPrincipal UsuarioLogado usuarioLogado) {
        Categoria categoria = categoriaRepository.getById(dto.getIdCategoria());
        Optional<Usuario> usuario = usuarioRepository.findByLogin(usuarioLogado.getUsername());
        if (usuario.isPresent()) {
            Produto produto = dto.toModel(usuario.get(), categoria);
            produtoRepository.save(produto);
            return ResponseEntity.ok().build();
        }
        throw new UsernameNotFoundException("Não foi possível encontrar usuário com email: " + usuarioLogado.getUsername());
    }

    @PostMapping(value = "/{id}/imagens")
    @Transactional
    public ResponseEntity<?> adicionaImagem(@PathVariable Long id, @Valid NovaImagemDTO dto, @AuthenticationPrincipal UsuarioLogado usuarioLogado) {

        Optional<Usuario> usuario = usuarioRepository.findByLogin(usuarioLogado.getUsername());
        Optional<Produto> produto = produtoRepository.findById(id);

        if (produto.isPresent() && usuario.isPresent()) {
            if (!produto.get().pertenceAoUsuario(usuario.get())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
            Set<String> links = uploaderFake.upload(dto.getImagens());
            produto.get().associaImagens(links);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
