package com.io.github.msj.mscurso.repository;

import com.io.github.msj.mscurso.enums.SituacaoInscricao;
import com.io.github.msj.mscurso.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Override
    long count();

    @Query("select count(c.situacaoInscricao) from Curso c where c.situacaoInscricao = ?1")
    long countBySituacaoInscricao(SituacaoInscricao situacaoInscricao);

}
