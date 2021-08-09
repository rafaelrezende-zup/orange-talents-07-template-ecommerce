package br.com.zup.mercadolivre.domain.dto;

import javax.validation.constraints.NotNull;

public class NovaCompraNFDTO {

    @NotNull
    private final Long idCompra;

    @NotNull
    private final Long idComprador;

    public NovaCompraNFDTO(Long idCompra, Long idComprador) {
        this.idCompra = idCompra;
        this.idComprador = idComprador;
    }

    @Override
    public String toString() {
        return "NovaCompraNFDTO{" +
                "idCompra=" + idCompra +
                ", idComprador=" + idComprador +
                '}';
    }

    public Long getIdCompra() {
        return idCompra;
    }

    public Long getIdComprador() {
        return idComprador;
    }
}
