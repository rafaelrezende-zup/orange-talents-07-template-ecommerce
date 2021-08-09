package br.com.zup.mercadolivre.component;

import br.com.zup.mercadolivre.domain.Compra;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class NovaCompraSucesso {

    private final Set<CompraSucesso> compraSucesso;

    public NovaCompraSucesso(Set<CompraSucesso> compraSucesso) {
        this.compraSucesso = compraSucesso;
    }

    public void processa(Compra compra) {
        if (compra.processadaComSucesso()) {
            compraSucesso.forEach(i -> i.processa(compra));
        }
    }
}
