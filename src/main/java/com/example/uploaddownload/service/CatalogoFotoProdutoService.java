package com.example.uploaddownload.service;

import com.example.uploaddownload.domain.FotoProduto;
import com.example.uploaddownload.domain.exception.EntidadeNaoEncontradaException;
import com.example.uploaddownload.repository.ProdutoRepository;
import com.example.uploaddownload.service.storage.FotoStorageService;
import com.example.uploaddownload.service.storage.FotoStorageService.NovaFoto;
import java.io.InputStream;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorage;

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
        Long produtoId = foto.getProduto().getId();
        String nomeNovoArquivo = fotoStorage.gerarNomeArquivo(foto.getNomeArquivo());
        String nomeArquivoExistente = null;

        Optional<FotoProduto> fotoExistente = produtoRepository
            .findFotoById(produtoId);

        if (fotoExistente.isPresent()) {
            nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
            produtoRepository.delete(fotoExistente.get());
        }

        foto.setNomeArquivo(nomeNovoArquivo);
        foto =  produtoRepository.save(foto);
        produtoRepository.flush();

        NovaFoto novaFoto = NovaFoto.builder()
            .nomeArquivo(foto.getNomeArquivo())
            .contentType(foto.getContentType())
            .inputStream(dadosArquivo)
            .build();

        fotoStorage.substituir(nomeArquivoExistente, novaFoto);

        return foto;
    }

    public FotoProduto buscarOuFalhar(Long produtoId) {
        return produtoRepository.findFotoById(produtoId)
            .orElseThrow(() -> new EntidadeNaoEncontradaException("Foto Produto não encontrada"));
    }

    @Transactional
    public void excluir(Long produtoId) {
        FotoProduto foto = buscarOuFalhar(produtoId);

        produtoRepository.delete(foto);
        produtoRepository.flush();

        fotoStorage.remover(foto.getNomeArquivo());
    }

}