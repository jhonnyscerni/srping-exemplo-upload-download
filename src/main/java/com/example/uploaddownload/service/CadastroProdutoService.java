package com.example.uploaddownload.service;

import com.example.uploaddownload.domain.Produto;
import com.example.uploaddownload.domain.exception.EntidadeNaoEncontradaException;
import com.example.uploaddownload.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto buscarOuFalhar(Long produtoId) {
        return produtoRepository.findById(produtoId)
            .orElseThrow(() -> new EntidadeNaoEncontradaException("Produto não encontrado "));
    }

}
