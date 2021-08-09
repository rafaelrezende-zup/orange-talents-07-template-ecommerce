package br.com.zup.mercadolivre.domain;

import br.com.zup.mercadolivre.domain.dto.GatewayPagamentoDTO;
import br.com.zup.mercadolivre.domain.dto.NovaCompraDTO;
import br.com.zup.mercadolivre.domain.enumeration.GatewayPagamento;
import br.com.zup.mercadolivre.domain.enumeration.StatusCompra;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "compra", cascade = CascadeType.MERGE)
    private final Set<Pagamento> pagamento = new HashSet<>();

    public Compra(NovaCompraDTO dto, Usuario usuario, Produto produto) {
        this.quantidade = dto.getQuantidade();
        this.valorNoMomento = produto.getValor();
        this.usuario = usuario;
        this.produto = produto;
        this.status = StatusCompra.INICIADA;
        this.gatewayPagamento = dto.getGatewayPagamento();
    }

    @Deprecated
    public Compra() {
    }

    public Long getId() {
        return id;
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

    public String urlDirecionamento(UriComponentsBuilder uriComponentsBuilder) {
        return this.gatewayPagamento.getUrlPagamento(this, uriComponentsBuilder);
    }

    public void adicionaPagamento(GatewayPagamentoDTO dto) {
        Pagamento pagamento = dto.toPagamento(this);
        Assert.isTrue(!this.pagamento.contains(pagamento), "Já existe um pagamento igual ao processado.");
        Assert.isTrue(pagamentosComSucesso().isEmpty(), "Essa compra já foi concluída com sucesso.");
        this.pagamento.add(pagamento);
    }

    private Set<Pagamento> pagamentosComSucesso() {
        Set<Pagamento> pagamentosComSucesso = this.pagamento.stream()
                .filter(Pagamento::pagamentoComSucesso)
                .collect(Collectors.toSet());
        Assert.isTrue(pagamentosComSucesso.size() <= 1,"Tem mais de um pagamento concluido com sucesso aqui na compra "+this.id);
        return pagamentosComSucesso;
    }

    public boolean processadaComSucesso() {
        return !pagamentosComSucesso().isEmpty();
    }
}
