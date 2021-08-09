package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.component.EnviaEmail;
import br.com.zup.mercadolivre.config.security.UsuarioLogado;
import br.com.zup.mercadolivre.domain.Compra;
import br.com.zup.mercadolivre.domain.Produto;
import br.com.zup.mercadolivre.domain.Usuario;
import br.com.zup.mercadolivre.domain.dto.NovaCompraDTO;
import br.com.zup.mercadolivre.repository.CompraRepository;
import br.com.zup.mercadolivre.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/compra")
public class CompraController {

    private final CompraRepository compraRepository;
    private final ProdutoRepository produtoRepository;
    private final EnviaEmail enviaEmail;

    public CompraController(CompraRepository compraRepository,
                            ProdutoRepository produtoRepository,
                            EnviaEmail enviaEmail) {
        this.compraRepository = compraRepository;
        this.produtoRepository = produtoRepository;
        this.enviaEmail = enviaEmail;
    }

    @PostMapping
    @Transactional
    public String novaCompra(@RequestBody @Valid NovaCompraDTO dto, UriComponentsBuilder uriComponentsBuilder, @AuthenticationPrincipal UsuarioLogado usuarioLogado) {
        Usuario usuario = usuarioLogado.toUsuario();
        Produto produto = produtoRepository.getById(dto.getIdProduto());
        boolean abate = produto.abateEstoque(dto.getQuantidade());
        if (abate) {
            Compra compra = new Compra(dto, usuario, produto);
            compraRepository.save(compra);
            enviaEmail.informaCompraAoVendedor(compra);
            return compra.urlDirecionamento(uriComponentsBuilder);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estoque insuficiente.");
    }

}
