package com.io.github.msj.mscurso.dto.response;


import com.io.github.msj.mscurso.enums.SituacaoInscricao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CursoResponseDTO {

    private Long idCurso;

    private String nome;

    private Integer numeroVagas;

    private SituacaoInscricao situacaoInscricao;
}
