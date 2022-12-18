package io.github.msj.mspessoa.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaInscritaReportResponseDTO {

    private String nome;

    private String sobrenome;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dataNascimento;

    private String cpf;

    private String email;

    private String cidade;

    private String estado;

    private BigDecimal nota;

    private String situacaoInscricao;

}
