package com.example.uploaddownload.repository;

import com.example.uploaddownload.domain.FotoProduto;

public interface ProdutoRepositoryQueries {

    FotoProduto save(FotoProduto foto);

    void delete(FotoProduto foto);

}

