package io.github.msj.mspessoa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaInscritaReportResponseDTO {

    private String nome;

    private String sobrenome;

    private LocalDate dataNascimento;

    private String cpf;

    private String email;

    private String cidade;

    private String estado;

    private String situacaoInscricao;

    private String curso;

    private String situacaoCurso;

    private Integer numeroVagasCurso;

}
