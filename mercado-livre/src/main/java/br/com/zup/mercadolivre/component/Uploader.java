package br.com.zup.mercadolivre.component;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface Uploader {

    Set<String> upload(List<MultipartFile> imagens);

}
