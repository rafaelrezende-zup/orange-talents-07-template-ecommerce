package br.com.zup.mercadolivre.domain.dto;

import br.com.zup.mercadolivre.domain.Produto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DetalheProdutoDTO {

    private final Long id;
    private final String nome;
    private final BigDecimal valor;
    private final String descricao;
    private Set<DetalheImagemProdutoDTO> imagens = new HashSet<>();
    private Set<DetalheCaracteristicaDTO> caracteristicas = new HashSet<>();
    private Set<DetalheOpiniaoDTO> opinioes = new HashSet<>();
    private Set<DetalhePerguntaDTO> perguntas = new HashSet<>();
    private final Double mediaNotaOpiniao;
    private final Long totalNotas;

    public DetalheProdutoDTO(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.valor = produto.getValor();
        this.descricao = produto.getDescricao();
        this.imagens = produto.getImagens().stream().map(DetalheImagemProdutoDTO::new).collect(Collectors.toSet());
        this.caracteristicas = produto.getCaracteristicas().stream().map(DetalheCaracteristicaDTO::new).collect(Collectors.toSet());
        this.opinioes = produto.getOpinioes().stream().map(DetalheOpiniaoDTO::new).collect(Collectors.toSet());
        this.perguntas = produto.getPerguntas().stream().map(DetalhePerguntaDTO::new).collect(Collectors.toSet());
        this.mediaNotaOpiniao = produto.calculaNotaMedia();
        this.totalNotas = produto.getQtdNota();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public Set<DetalheImagemProdutoDTO> getImagens() {
        return imagens;
    }

    public Set<DetalheCaracteristicaDTO> getCaracteristicas() {
        return caracteristicas;
    }

    public Set<DetalheOpiniaoDTO> getOpinioes() {
        return opinioes;
    }

    public Set<DetalhePerguntaDTO> getPerguntas() {
        return perguntas;
    }

    public Double getMediaNotaOpiniao() {
        return mediaNotaOpiniao;
    }

    public Long getTotalNotas() {
        return totalNotas;
    }
}
