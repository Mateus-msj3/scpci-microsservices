package io.github.msj.mspessoa.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.msj.mspessoa.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaResponseDTO {

    private Long id;

    private String nome;

    private String sobrenome;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dataNascimento;

    private String cpf;

    private String email;

    private Endereco endereco;
}
