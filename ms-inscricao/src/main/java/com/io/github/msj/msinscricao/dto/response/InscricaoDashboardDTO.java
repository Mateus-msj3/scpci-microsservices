package com.io.github.msj.msinscricao.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InscricaoDashboardDTO {

    private Long maiorInscricao;

    private Long menorInscricao;

}
