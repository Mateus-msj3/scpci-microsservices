package com.io.github.msj.mscurso.model;

import com.io.github.msj.mscurso.enums.SituacaoInscricao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "É necessário informar o nome.")
    private String nome;

    @NotNull(message = "É necessário informar o número de vagas.")
    private Integer numeroVagas;

    @Enumerated(value = EnumType.STRING)
    private SituacaoInscricao situacaoInscricao;

}
