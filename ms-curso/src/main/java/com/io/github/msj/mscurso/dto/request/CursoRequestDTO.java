package com.io.github.msj.mscurso.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CursoRequestDTO {

    private String nome;

    private Integer numeroVagas;

}

