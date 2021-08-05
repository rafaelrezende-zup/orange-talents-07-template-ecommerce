package br.com.zup.mercadolivre.domain.dto;

import javax.validation.constraints.NotBlank;

public class NovaPerguntaDTO {

    @NotBlank
    private String titulo;

    public String getTitulo() {
        return titulo;
    }
}
