package com.io.github.msj.mscurso.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CursoDashboardDTO {

    private Long quantidadeCursosCadastrados;

    private Long quantidadeCursosEmAndamento;

    private Long quantidadeCursosFinalizados;

}
