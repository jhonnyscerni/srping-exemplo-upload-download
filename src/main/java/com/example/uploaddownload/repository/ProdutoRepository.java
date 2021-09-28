package com.example.uploaddownload.repository;

import com.example.uploaddownload.domain.FotoProduto;
import com.example.uploaddownload.domain.Produto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQueries {

    @Query("select f from FotoProduto f join f.produto p "
        + "where f.produto.id = :produtoId")
    Optional<FotoProduto> findFotoById(Long produtoId);
}
