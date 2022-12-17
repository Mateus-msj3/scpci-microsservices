package io.github.msj.mspessoa.infra;

import io.github.msj.mspessoa.dto.response.InscricaoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "msinscricao", path = "/inscricoes")
public interface InscricaoResourceClient {

    @GetMapping("/pessoasInscritas/{idCurso}")
    ResponseEntity<List<InscricaoResponseDTO>> buscarPessoasInscritas(@PathVariable Integer idCurso);
}
