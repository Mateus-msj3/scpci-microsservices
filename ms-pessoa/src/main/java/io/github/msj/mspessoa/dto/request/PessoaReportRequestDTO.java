package io.github.msj.mspessoa.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaReportRequestDTO {

    private Long idCurso;

    private String nome;

    private Integer numeroVagas;

    private String situacaoInscricao;
}
