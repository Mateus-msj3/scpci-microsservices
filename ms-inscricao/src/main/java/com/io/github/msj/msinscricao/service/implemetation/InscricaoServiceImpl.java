package com.io.github.msj.msinscricao.service.implemetation;

import com.io.github.msj.msinscricao.dto.request.InscricaoFinalizacaoRequestDTO;
import com.io.github.msj.msinscricao.dto.request.InscricaoRequestDTO;
import com.io.github.msj.msinscricao.dto.response.*;
import com.io.github.msj.msinscricao.enums.Situacao;
import com.io.github.msj.msinscricao.exception.NegocioException;
import com.io.github.msj.msinscricao.infra.mqueue.FinalizarInscricaoPublisher;
import com.io.github.msj.msinscricao.model.Inscricao;
import com.io.github.msj.msinscricao.repository.InscricaoRepository;
import com.io.github.msj.msinscricao.service.CursoClientService;
import com.io.github.msj.msinscricao.service.InscricaoService;
import com.io.github.msj.msinscricao.service.PessoaClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InscricaoServiceImpl implements InscricaoService {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private CursoClientService cursoClientService;

    @Autowired
    private PessoaClientService pessoaClientService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FinalizarInscricaoPublisher finalizarInscricaoPublisher;

    @Override
    @Transactional
    public InscricaoMensagemResponseDTO salvar(InscricaoRequestDTO inscricaoRequestDTO) {
        List<Inscricao> inscricoesEncontradas = this.buscarInscricaoPorIdCurso(inscricaoRequestDTO.getIdCurso());
        for (Inscricao inscricao : inscricoesEncontradas) {
            if (inscricao.getCpf().equals(inscricaoRequestDTO.getCpf())) {
                throw new NegocioException("O candidato com cpf: " + inscricaoRequestDTO.getCpf() + " já está inscrito no curso.");
            }
        }
        Inscricao inscricaoRequest = modelMapper.map(inscricaoRequestDTO, Inscricao.class);
        inscricaoRepository.save(inscricaoRequest);
        return new InscricaoMensagemResponseDTO("Inscrição realizada com sucesso.");
    }

    @Override
    public InscricaoMensagemResponseDTO finalizar(InscricaoFinalizacaoRequestDTO inscricaoFinalizacaoRequestDTO) {
        try {
            finalizarInscricaoPublisher.finalizarInscricao(inscricaoFinalizacaoRequestDTO);
            return new InscricaoMensagemResponseDTO("Inscrição finalizada com sucesso.");

        } catch (Exception e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @Override
    public List<InscricaoResponseDTO> listarInscricaoPorCurso(Integer idCurso) {
        List<Inscricao> inscricoesEncontradas = this.buscarInscricaoPorIdCurso(idCurso);
        List<InscricaoResponseDTO> inscricaoResponseDTOS = new ArrayList<>();
        for (Inscricao inscricao : inscricoesEncontradas) {
            PessoaResponseDTO pessoa = pessoaClientService.buscarPessoaPorCpf(inscricao.getCpf());
            if (pessoa != null) {
                InscricaoResponseDTO inscricaoResponseDTO = new InscricaoResponseDTO();
                inscricaoResponseDTO.setNomeInscrito(pessoa.getNome());
                inscricaoResponseDTO.setSobrenome(pessoa.getSobrenome());
                inscricaoResponseDTO.setCpf(pessoa.getCpf());
                inscricaoResponseDTO.setNota(inscricao.getNota());
                inscricaoResponseDTOS.add(inscricaoResponseDTO);
            }
        }
        return inscricaoResponseDTOS;
    }

    @Override
    public List<InscricaoResponseDTO> inscritosFinalizados(Integer idCurso) {
        List<Inscricao> inscricoesEncontradas = this.buscarInscricaoPorIdCurso(idCurso);
        List<InscricaoResponseDTO> inscricaoResponseDTOS = new ArrayList<>();
        for (Inscricao inscricao : inscricoesEncontradas) {
            if (inscricao.getSituacao().equals(Situacao.SELECIONADO)) {
                PessoaResponseDTO pessoa = pessoaClientService.buscarPessoaPorCpf(inscricao.getCpf());
                if (pessoa != null) {
                    InscricaoResponseDTO inscricaoResponseDTO = new InscricaoResponseDTO();
                    inscricaoResponseDTO.setNomeInscrito(pessoa.getNome());
                    inscricaoResponseDTO.setSobrenome(pessoa.getSobrenome());
                    inscricaoResponseDTO.setCpf(pessoa.getCpf());
                    inscricaoResponseDTO.setNota(inscricao.getNota());
                    inscricaoResponseDTOS.add(inscricaoResponseDTO);
                }
            }
        }
        return inscricaoResponseDTOS;
    }

    @Override
    public Long quantidadePessoasInscritasNumCurso() {
        return inscricaoRepository.countByCpf();
    }

    private List<Inscricao> buscarInscricaoPorIdCurso(Integer idCurso) {
        return this.inscricaoRepository.findByIdCurso(idCurso);
    }

    @Override
    public InscricaoDashboardDTO dadosDashboard() {
        List<Inscricao> inscricoesEncontradas = inscricaoRepository.findAll();
        Long menorInscricao = 0L;
        Long maiorInscricao = 0L;
        Long quantidadeInscritosPorCurso = 0L;
        for (Inscricao inscricao : inscricoesEncontradas) {
            quantidadeInscritosPorCurso = this.inscricaoRepository.countByInscricaoCurso(inscricao.getIdCurso());

            if (quantidadeInscritosPorCurso > maiorInscricao) {
                maiorInscricao = quantidadeInscritosPorCurso;
            }
            if ((menorInscricao == 0) || menorInscricao > quantidadeInscritosPorCurso) {
                menorInscricao = quantidadeInscritosPorCurso;
            }
        }
        return InscricaoDashboardDTO.builder().maiorInscricao(maiorInscricao).menorInscricao(menorInscricao).build();
    }

    @Override
    public List<InscricaoResponseDTO> pessoasInscritas(Integer idCurso) {
        List<Inscricao> inscricoes = this.buscarInscricaoPorIdCurso(idCurso);
        return inscricoes.stream()
                .map(inscricao -> modelMapper.map(inscricao, InscricaoResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Long quantidadeInscritosSelecionados(Integer idCurso) {
        return inscricaoRepository.countByIdCursoAndSituacao(idCurso, Situacao.SELECIONADO);
    }

    @Override
    public Long quantidadeInscritosNaoSelecionados(Integer idCurso) {
        return inscricaoRepository.countByIdCursoAndSituacao(idCurso, Situacao.NAO_SELECIONADO);
    }

}
