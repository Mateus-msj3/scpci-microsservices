package com.io.github.msj.mscurso;

import com.io.github.msj.mscurso.dto.request.CursoRequestDTO;
import com.io.github.msj.mscurso.dto.response.CursoResponseDTO;
import com.io.github.msj.mscurso.dto.response.CursoSalvoResponseDTO;
import com.io.github.msj.mscurso.enums.SituacaoInscricao;
import com.io.github.msj.mscurso.model.Curso;
import com.io.github.msj.mscurso.repository.CursoRepository;
import com.io.github.msj.mscurso.service.implemetation.CursoServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CursoServiceTest {

    @InjectMocks
    CursoServiceImpl cursoService;

    @Mock
    CursoRepository cursoRepository;

    @Mock
    ModelMapper modelMapper;

    private final Long ID = 1L;

    @Test
    @DisplayName("Deve listar todos os cursos")
    public void listarTodos(){

        var curso1 = Curso.builder().id(ID).nome("Curso de Informática").numeroVagas(10).situacaoInscricao(SituacaoInscricao.EM_ANDAMENTO).build();
        var curso2 = Curso.builder().id(2L).nome("Curso de Auxiliar Administrativo").numeroVagas(35).situacaoInscricao(SituacaoInscricao.EM_ANDAMENTO).build();

        var retorno1 = CursoResponseDTO.builder().idCurso(curso1.getId()).nome(curso1.getNome()).situacaoInscricao(curso1.getSituacaoInscricao()).build();
        var retorno2 = CursoResponseDTO.builder().idCurso(curso2.getId()).nome(curso2.getNome()).situacaoInscricao(curso2.getSituacaoInscricao()).build();

        List<Curso> cursos = Arrays.asList(curso1, curso2);

        when(cursoRepository.findAll()).thenReturn(cursos);
        when(modelMapper.map(any(), any())).thenReturn(retorno1, retorno2);

        List<CursoResponseDTO> retorno = cursoService.listarTodos();

        verify(cursoRepository, times(1)).findAll();

        assertNotNull(retorno, "Verfica se o retorno é diferente de null");
    }

    @Test
    @DisplayName("Deve salvar uma inscrição")
    public void salvar(){

        var cursoRequest = CursoRequestDTO.builder().nome("Curso Teste").numeroVagas(22).build();
        var curso = Curso.builder().nome("Curso Teste").numeroVagas(22).build();
        var cursoSalvo = Curso.builder().id(ID).nome("Curso Teste").numeroVagas(22).situacaoInscricao(SituacaoInscricao.EM_ANDAMENTO).build();
        var retorno1 = CursoSalvoResponseDTO.builder().idCurso(cursoSalvo.getId()).build();

        when(modelMapper.map(cursoRequest, Curso.class)).thenReturn(curso);
        when(cursoRepository.save(curso)).thenReturn(cursoSalvo);
        when(modelMapper.map(cursoSalvo, CursoSalvoResponseDTO.class)).thenReturn(retorno1);

        CursoSalvoResponseDTO retorno = cursoService.salvar(cursoRequest);

        verify(cursoRepository, times(1)).save(eq(curso));

        assertNotNull(retorno, "Verfica se o retorno é diferente de null");
        assertEquals(retorno.getMensagem(), "Curso salvo com sucesso!");
    }

}
