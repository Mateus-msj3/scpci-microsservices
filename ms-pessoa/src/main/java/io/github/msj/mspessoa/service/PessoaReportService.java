package io.github.msj.mspessoa.service;

import io.github.msj.mspessoa.dto.request.PessoaReportRequestDTO;
import io.github.msj.mspessoa.dto.response.CursoResponseDTO;
import io.github.msj.mspessoa.dto.response.InscricaoResponseDTO;
import io.github.msj.mspessoa.dto.response.PessoaInscritaReportResponseDTO;
import io.github.msj.mspessoa.dto.response.PessoaResponseDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

@Service
public class PessoaReportService {

    private final String LOCAL_ARMAZENAMENTO_RELATORIO_JASPER = "/home/dev/Dev/projects-Java/scpci-microsservice/ms-pessoa/src/main/java/io/github/msj/mspessoa/report/pessoas-inscritas.jrxml";

    private final String LOCAL_ARMAZENAMENTO_IMG_LOGO = "/home/dev/Dev/projects-Java/scpci-microsservice/ms-pessoa/src/main/java/io/github/msj/mspessoa/report/images/logo_scpci-web.png";

    private final String LOCAL_ARMAZENAMENTO_RELATORIO_PDF = "/home/dev/Downloads/pessoas-inscritas.pdf";


    @Autowired
    PessoaService pessoaService;

    @Autowired
    InscricaoClientService inscricaoClientService;

    @Autowired
    CursoClientService cursoClientService;

    public String relatorioPessoasInscritas(PessoaReportRequestDTO pessoaReportRequestDTO) throws JRException, FileNotFoundException {
        List<PessoaInscritaReportResponseDTO> pessoasInscritas = new ArrayList<>();

        List<InscricaoResponseDTO> inscricoes = inscricaoClientService.pessoasInscritas(pessoaReportRequestDTO.getIdCurso().intValue());
        Long quantidadeDeSelecionadosNoCurso = inscricaoClientService.quantidadeDeSelecionados(pessoaReportRequestDTO.getIdCurso().intValue());
        Long quantidadeDeNaoSelecionadosNoCurso = inscricaoClientService.quantidadeDeNaoSelecionados(pessoaReportRequestDTO.getIdCurso().intValue());

        File logo = ResourceUtils.getFile(LOCAL_ARMAZENAMENTO_IMG_LOGO);

        if (inscricoes != null) {
            for (InscricaoResponseDTO inscricao : inscricoes) {
                PessoaResponseDTO pessoa = pessoaService.buscarPorCpf(inscricao.getCpf());

                PessoaInscritaReportResponseDTO pessoaReport = PessoaInscritaReportResponseDTO
                        .builder()
                        .nome(pessoa.getNome())
                        .sobrenome(pessoa.getSobrenome())
                        .email(pessoa.getEmail())
                        .dataNascimento(pessoa.getDataNascimento())
                        .cpf(pessoa.getCpf())
                        .cidade(pessoa.getEndereco().getCidade())
                        .estado(pessoa.getEndereco().getEstado())
                        .nota(inscricao.getNota())
                        .situacaoInscricao(inscricao.getSituacao())
                        .build();

                pessoasInscritas.add(pessoaReport);
            }

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("logo_sistema", new FileInputStream(logo));
            parametros.put("nomeCurso", pessoaReportRequestDTO.getNome());
            parametros.put("situacaoCurso", pessoaReportRequestDTO.getSituacaoInscricao());
            parametros.put("numeroVagasCurso", pessoaReportRequestDTO.getNumeroVagas());
            parametros.put("quantidade_selecionados", quantidadeDeSelecionadosNoCurso);
            parametros.put("quantidade_nao_selecionados", quantidadeDeNaoSelecionadosNoCurso);

            return this.gerarRelatorio(pessoasInscritas, parametros);
        }
        return null;
    }

    public String gerarRelatorio(List<PessoaInscritaReportResponseDTO> pessoas, Map<String, Object> parametros) throws FileNotFoundException, JRException {
        File file = ResourceUtils.getFile(LOCAL_ARMAZENAMENTO_RELATORIO_JASPER);

        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(pessoas);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

        JasperExportManager.exportReportToPdfFile(jasperPrint, LOCAL_ARMAZENAMENTO_RELATORIO_PDF);

        return "Relatório gerado com sucesso: " + LOCAL_ARMAZENAMENTO_RELATORIO_PDF;
    }
}
