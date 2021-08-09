package br.com.zup.mercadolivre.domain;

import br.com.zup.mercadolivre.domain.dto.NovaCompraDTO;
import br.com.zup.mercadolivre.domain.enumeration.GatewayPagamento;
import br.com.zup.mercadolivre.domain.enumeration.StatusCompra;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive
    private Integer quantidade;

    private BigDecimal valorNoMomento;

    @NotNull
    @ManyToOne
    private Usuario usuario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusCompra status;

    @NotNull
    @Enumerated(EnumType.STRING)
    private GatewayPagamento gatewayPagamento;

    @Deprecated
    public Compra() {
    }

    public Compra(NovaCompraDTO dto, Usuario usuario, Produto produto) {
        this.quantidade = dto.getQuantidade();
        this.valorNoMomento = produto.getValor();
        this.usuario = usuario;
        this.produto = produto;
        this.status = StatusCompra.INICIADA;
        this.gatewayPagamento = dto.getGatewayPagamento();
    }

    public Long getId() {
        return id;
    }

    public String urlDirecionamento(UriComponentsBuilder uriComponentsBuilder) {
        return this.gatewayPagamento.getUrlPagamento(this, uriComponentsBuilder);
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValorNoMomento() {
        return valorNoMomento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Produto getProduto() {
        return produto;
    }

    public StatusCompra getStatus() {
        return status;
    }

    public GatewayPagamento getGatewayPagamento() {
        return gatewayPagamento;
    }
}
