package io.github.msj.mspessoa.service;

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
import java.io.FileNotFoundException;
import java.util.*;

@Service
public class PessoaReportService {

    private final String LOCAL_ARMAZENAMENTO_RELATORIO_JASPER = "/home/dev/Dev/projects-Java/scpci-microsservice/ms-pessoa/src/main/java/io/github/msj/mspessoa/report/pessoas-inscritas.jrxml";

    private final String LOCAL_ARMAZENAMENTO_RELATORIO_PDF = "/home/dev/Downloads/pessoas-inscritas.pdf";


    @Autowired
    PessoaService pessoaService;

    @Autowired
    InscricaoClientService inscricaoClientService;

    @Autowired
    CursoClientService cursoClientService;

    public List<PessoaInscritaReportResponseDTO> dadosPessoasInscritas(Integer idCurso) {
        List<PessoaInscritaReportResponseDTO> pessoasInscritas = new ArrayList<>();
        List<InscricaoResponseDTO> inscricoes = inscricaoClientService.pessoasInscritas(idCurso);
        CursoResponseDTO curso = cursoClientService.buscarCurso(idCurso.longValue());

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
                        .situacaoInscricao(inscricao.getSituacao())
                        .curso(curso.getNome())
                        .situacaoCurso(curso.getSituacaoInscricao())
                        .numeroVagasCurso(curso.getNumeroVagas())
                        .build();

                pessoasInscritas.add(pessoaReport);
            }
            return pessoasInscritas;
        }
        return null;
    }

    public String gerarRelatorioPessoasInscritas(Integer idCurso) throws FileNotFoundException, JRException {
        List<PessoaInscritaReportResponseDTO> pessoas = this.dadosPessoasInscritas(idCurso);

        File file = ResourceUtils.getFile(LOCAL_ARMAZENAMENTO_RELATORIO_JASPER);
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        //Lista com os dados que preenche o relatório
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(pessoas);

        //Parametros que vai para o relatório
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("Desenvolvido por:", "Mateus Souza");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

        //Salvar em formato pdf
        JasperExportManager.exportReportToPdfFile(jasperPrint, LOCAL_ARMAZENAMENTO_RELATORIO_PDF);

        return "Relatório gerado com sucesso: " + LOCAL_ARMAZENAMENTO_RELATORIO_PDF;
    }
}
