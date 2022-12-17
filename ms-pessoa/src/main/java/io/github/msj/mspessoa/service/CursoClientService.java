package io.github.msj.mspessoa.service;

import io.github.msj.mspessoa.dto.response.CursoResponseDTO;
import io.github.msj.mspessoa.dto.response.InscricaoResponseDTO;
import io.github.msj.mspessoa.infra.CursoResourceClient;
import io.github.msj.mspessoa.infra.InscricaoResourceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoClientService {

    @Autowired
    private CursoResourceClient cursoResourceClient;

    CursoResponseDTO buscarCurso(Long idCurso) {
        ResponseEntity<CursoResponseDTO> curso = cursoResourceClient.listarPorId(idCurso);
        CursoResponseDTO cursoEncontrado = curso.getBody();
        if (cursoEncontrado != null) {
            return CursoResponseDTO
                    .builder()
                    .nome(cursoEncontrado.getNome())
                    .situacaoInscricao(cursoEncontrado.getSituacaoInscricao())
                    .numeroVagas(cursoEncontrado.getNumeroVagas())
                    .build();
        }
        return null;
    }
}
