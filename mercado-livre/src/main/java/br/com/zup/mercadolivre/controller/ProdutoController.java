package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.component.Uploader;
import br.com.zup.mercadolivre.config.security.UsuarioLogado;
import br.com.zup.mercadolivre.domain.*;
import br.com.zup.mercadolivre.domain.dto.NovaImagemDTO;
import br.com.zup.mercadolivre.domain.dto.NovaOpiniaoDTO;
import br.com.zup.mercadolivre.domain.dto.NovoProdutoDTO;
import br.com.zup.mercadolivre.repository.*;
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
    private final ImagemProdutoRepository imagemProdutoRepository;
    private final OpiniaoRepository opiniaoRepository;
    private final Uploader uploader;

    public ProdutoController(ProdutoRepository produtoRepository,
                             CategoriaRepository categoriaRepository,
                             UsuarioRepository usuarioRepository,
                             ImagemProdutoRepository imagemProdutoRepository,
                             OpiniaoRepository opiniaoRepository,
                             Uploader uploader) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
        this.imagemProdutoRepository = imagemProdutoRepository;
        this.opiniaoRepository = opiniaoRepository;
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
            Set<String> links = uploader.upload(dto.getImagens());
            Set<ImagemProduto> imagens = produto.get().associaImagens(links);
            imagens.forEach(imagemProdutoRepository::save);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/{id}/opiniao")
    @Transactional
    public ResponseEntity<?> adicionaOpiniao(@PathVariable Long id, @RequestBody @Valid NovaOpiniaoDTO dto, @AuthenticationPrincipal UsuarioLogado usuarioLogado) {
        Optional<Usuario> usuario = usuarioRepository.findByLogin(usuarioLogado.getUsername());
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isPresent() && usuario.isPresent()) {
            Opiniao opiniao = new Opiniao(dto, usuario.get(), produto.get());
            opiniaoRepository.save(opiniao);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
