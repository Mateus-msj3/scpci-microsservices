package com.io.github.msj.mscurso.dto.request;

import com.io.github.msj.mscurso.enums.SituacaoInscricao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CursoSituacaoInscricaoRequestDTO {

    private SituacaoInscricao situacaoInscricao;

}

