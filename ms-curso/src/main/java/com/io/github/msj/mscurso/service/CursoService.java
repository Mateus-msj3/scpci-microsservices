package com.io.github.msj.mscurso.service;

import com.io.github.msj.mscurso.dto.request.CursoRequestDTO;
import com.io.github.msj.mscurso.dto.request.CursoSituacaoInscricaoRequestDTO;
import com.io.github.msj.mscurso.dto.response.CursoResponseDTO;
import com.io.github.msj.mscurso.dto.response.CursoSalvoResponseDTO;
import com.io.github.msj.mscurso.dto.response.CursoSituacaoInscricaoResponseDTO;

import java.util.List;

public interface CursoService {

    List<CursoResponseDTO> listarTodos();

    CursoResponseDTO listarPorId(Long id);

    CursoSalvoResponseDTO salvar(CursoRequestDTO cursoRequestDTO);

    CursoSituacaoInscricaoResponseDTO editarSituacaoInscricao(Long idCurso, CursoSituacaoInscricaoRequestDTO cursoSituacaoInscricaoRequestDTO);

}
