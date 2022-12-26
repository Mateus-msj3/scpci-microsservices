package io.github.msj.mspessoa.controller;

import io.github.msj.mspessoa.dto.request.PessoaReportRequestDTO;
import io.github.msj.mspessoa.dto.request.PessoaRequestDTO;
import io.github.msj.mspessoa.dto.response.PessoaResponseDTO;
import io.github.msj.mspessoa.service.PessoaReportService;
import io.github.msj.mspessoa.service.PessoaService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.util.List;
@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private PessoaReportService pessoaReportService;

    @GetMapping
    public ResponseEntity<List<PessoaResponseDTO>> listarTodos() {
        List<PessoaResponseDTO> list = pessoaService.listarTodos();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> listarPorId(@PathVariable Long id) {
        PessoaResponseDTO pessoaResponseDTO = pessoaService.listarPorId(id);
        return ResponseEntity.ok().body(pessoaResponseDTO);
    }

    @GetMapping("/cpf")
    public ResponseEntity<PessoaResponseDTO> buscarPorCpf(@RequestParam("cpf") String cpf) {
        PessoaResponseDTO pessoaResponseDTO = pessoaService.buscarPorCpf(cpf);
        return ResponseEntity.ok().body(pessoaResponseDTO);
    }

    @PostMapping
    public ResponseEntity<PessoaResponseDTO> salvar(@RequestBody @Valid PessoaRequestDTO pessoaRequestDTO) {
        PessoaResponseDTO pessoaResponseDTO = pessoaService.salvar(pessoaRequestDTO);
        return ResponseEntity.ok().body(pessoaResponseDTO);
    }

    @PutMapping("/{idPessoa}")
    public ResponseEntity<PessoaResponseDTO> editar(@PathVariable Long idPessoa, @RequestBody @Valid PessoaRequestDTO pessoaRequestDTO) {
        pessoaRequestDTO.setId(idPessoa);
        PessoaResponseDTO pessoaResponseDTO = pessoaService.editar(idPessoa, pessoaRequestDTO);
        return ResponseEntity.ok().body(pessoaResponseDTO);
    }

    @DeleteMapping("/{idPessoa}")
    public ResponseEntity<?> delete(@PathVariable Long idPessoa) {
        pessoaService.deletar(idPessoa);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/quantidade_pessoas_cadastradas")
    public ResponseEntity<Long> buscarQuantidadePessoas() {
        return ResponseEntity.ok().body(pessoaService.quantidadePessoasCadastradas());
    }

    @PostMapping("/relatorio-pessoas-inscritas")
    public ResponseEntity<byte[]> gerarRelatorioPessoasInscritas(@RequestBody PessoaReportRequestDTO pessoaReportRequestDTO) throws JRException, FileNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=pessoas-inscritas.pdf");
        byte [] report = pessoaReportService.relatorioPessoasInscritas(pessoaReportRequestDTO);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(report);
    }

}
