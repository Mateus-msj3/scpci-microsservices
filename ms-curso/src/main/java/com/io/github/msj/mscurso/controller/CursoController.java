package com.io.github.msj.mscurso.controller;

import com.io.github.msj.mscurso.dto.request.CursoRequestDTO;
import com.io.github.msj.mscurso.dto.request.CursoSituacaoInscricaoRequestDTO;
import com.io.github.msj.mscurso.dto.response.CursoResponseDTO;
import com.io.github.msj.mscurso.dto.response.CursoSalvoResponseDTO;
import com.io.github.msj.mscurso.dto.response.CursoSituacaoInscricaoResponseDTO;
import com.io.github.msj.mscurso.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

        @GetMapping
        public ResponseEntity<List<CursoResponseDTO>> listarTodos() {
        List<CursoResponseDTO> list = cursoService.listarTodos();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> listarPorId(@PathVariable Long id) {
        CursoResponseDTO cursoResponseDTO = cursoService.listarPorId(id);
        return ResponseEntity.ok().body(cursoResponseDTO);
    }

    @PostMapping("/salvar")
    public ResponseEntity<CursoSalvoResponseDTO> salvar(@RequestBody @Valid CursoRequestDTO cursoRequestDTO) {
        CursoSalvoResponseDTO cursoSalvoResponseDTO = cursoService.salvar(cursoRequestDTO);
        return ResponseEntity.ok().body(cursoSalvoResponseDTO);
    }

    @PutMapping("/editar/{idCurso}")
    public ResponseEntity<CursoSituacaoInscricaoResponseDTO> atualizarSituacaoInscricao(@PathVariable Long idCurso, @RequestBody CursoSituacaoInscricaoRequestDTO cursoSituacaoInscricaoRequestDTO) {
        CursoSituacaoInscricaoResponseDTO cursoSituacaoInscricaoResponseDTO = cursoService.editarSituacaoInscricao(idCurso, cursoSituacaoInscricaoRequestDTO);
        return ResponseEntity.ok().body(cursoSituacaoInscricaoResponseDTO);
    }

}
