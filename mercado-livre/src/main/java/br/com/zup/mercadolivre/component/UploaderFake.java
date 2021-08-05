package br.com.zup.mercadolivre.component;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Primary
public class UploaderFake implements Uploader {

    public Set<String> upload(List<MultipartFile> imagens) {
        return imagens.stream()
                .map(i -> "http://zup.com.br/mercadolivre/"
                        + i.getName() + "-"
                        + UUID.randomUUID())
                .collect(Collectors.toSet());
    }

}
