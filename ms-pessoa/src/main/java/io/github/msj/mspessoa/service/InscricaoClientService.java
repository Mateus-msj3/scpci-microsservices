package io.github.msj.mspessoa.service;

import io.github.msj.mspessoa.dto.response.InscricaoResponseDTO;
import io.github.msj.mspessoa.infra.InscricaoResourceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class InscricaoClientService {

    @Autowired
    private InscricaoResourceClient inscricaoResourceClient;

    List<InscricaoResponseDTO> pessoasInscritas(Integer idCurso) {
        ResponseEntity<List<InscricaoResponseDTO>> pessoas = inscricaoResourceClient.buscarPessoasInscritas(idCurso);
        List<InscricaoResponseDTO> inscricoes = pessoas.getBody();
        if (!inscricoes.isEmpty()) {
            return inscricoes;
        }
        return null;
    }
}
