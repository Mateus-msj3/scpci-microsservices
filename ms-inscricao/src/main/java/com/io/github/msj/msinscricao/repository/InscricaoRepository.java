package com.io.github.msj.msinscricao.repository;

import com.io.github.msj.msinscricao.enums.Situacao;
import com.io.github.msj.msinscricao.model.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {
    List<Inscricao> findByIdCurso (Integer idCurso);

    boolean existsByCpf(String cpf);

    @Query("select count(distinct cpf) from Inscricao")
    long countByCpf();

    @Query("select count(i.cpf) from Inscricao i where i.idCurso = ?1")
    long countByInscricaoCurso(Integer idCurso);

    @Query("select count(i) from Inscricao i where i.idCurso = ?1 and i.situacao = ?2")
    long countByIdCursoAndSituacao(Integer idCurso, Situacao situacao);

}
