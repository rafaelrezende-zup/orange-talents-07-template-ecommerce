package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.component.EnviaEmail;
import br.com.zup.mercadolivre.component.Uploader;
import br.com.zup.mercadolivre.config.security.UsuarioLogado;
import br.com.zup.mercadolivre.domain.*;
import br.com.zup.mercadolivre.domain.dto.*;
import br.com.zup.mercadolivre.repository.*;
import br.com.zup.mercadolivre.validator.ProibeCaracteristicaComNomeIgualValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final ImagemProdutoRepository imagemProdutoRepository;
    private final OpiniaoRepository opiniaoRepository;
    private final PerguntaRepository perguntaRepository;
    private final Uploader uploader;
    private final EnviaEmail enviaEmail;

    public ProdutoController(ProdutoRepository produtoRepository,
                             CategoriaRepository categoriaRepository,
                             ImagemProdutoRepository imagemProdutoRepository,
                             OpiniaoRepository opiniaoRepository,
                             PerguntaRepository perguntaRepository,
                             Uploader uploader,
                             EnviaEmail enviaEmail) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.imagemProdutoRepository = imagemProdutoRepository;
        this.opiniaoRepository = opiniaoRepository;
        this.perguntaRepository = perguntaRepository;
        this.uploader = uploader;
        this.enviaEmail = enviaEmail;
    }

    @InitBinder(value = "novoProdutoDTO")
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new ProibeCaracteristicaComNomeIgualValidator());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cria(@RequestBody @Valid NovoProdutoDTO dto, @AuthenticationPrincipal UsuarioLogado usuarioLogado) {
        Categoria categoria = categoriaRepository.getById(dto.getIdCategoria());
        Usuario usuario = usuarioLogado.toUsuario();
        Produto produto = dto.toModel(usuario, categoria);
        produtoRepository.save(produto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DetalheProdutoDTO> detalhe(@PathVariable Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.map(value -> ResponseEntity.ok(new DetalheProdutoDTO(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/{id}/imagens")
    @Transactional
    public ResponseEntity<?> adicionaImagem(@PathVariable Long id, @Valid NovaImagemDTO dto, @AuthenticationPrincipal UsuarioLogado usuarioLogado) {
        Usuario usuario = usuarioLogado.toUsuario();
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isPresent()) {
            if (!produto.get().pertenceAoUsuario(usuario)) {
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
        Usuario usuario = usuarioLogado.toUsuario();
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isPresent()) {
            Opiniao opiniao = new Opiniao(dto, usuario, produto.get());
            opiniaoRepository.save(opiniao);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/{id}/pergunta")
    @Transactional
    public ResponseEntity<?> adicionaPergunta(@PathVariable Long id, @RequestBody @Valid NovaPerguntaDTO dto, @AuthenticationPrincipal UsuarioLogado usuarioLogado) {
        Usuario usuario = usuarioLogado.toUsuario();
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isPresent()) {
            Pergunta pergunta = new Pergunta(dto, usuario, produto.get());
            perguntaRepository.save(pergunta);
            enviaEmail.enviaPerguntaAoVendedor(pergunta);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
