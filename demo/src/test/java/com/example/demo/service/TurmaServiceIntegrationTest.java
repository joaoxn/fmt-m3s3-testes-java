package com.example.demo.service;

import com.example.demo.database.entities.Estudante;
import com.example.demo.database.entities.Turma;
import com.example.demo.database.repositories.TurmaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TurmaServiceIntegrationTest {
    private final Long defaultId = 0L;

    @MockBean
    private TurmaRepository turmaRepository;

    @Autowired
    private TurmaService service;

    @Test
    void testCadastrarTurma() {
        String nome = "testNome";

        Turma result = service.cadastrarTurma(nome);

        assertEquals(nome, result.getNome());
        verify(turmaRepository, times(1)).save(any(Turma.class));
    }

    @Test
    void testListarTurmas() {
        service.listarTurmas();
        verify(turmaRepository, times(1)).findAll();
    }

    @Test
    void testBuscarTurmaPorId() {
        assertThrows(RuntimeException.class, () -> service.buscarTurmaPorId(defaultId));
        verify(turmaRepository, times(1)).findById(defaultId);
    }

    @Test
    void testAtualizarTurma() {
        Turma turma = new Turma();
        String novoNome = "testNome";

        when(turmaRepository.findById(defaultId)).thenReturn(Optional.of(turma));
        Turma result = service.atualizarTurma(defaultId, novoNome);

        assertEquals(novoNome, result.getNome());
        verify(turmaRepository, times(1)).findById(defaultId);
    }

    @Test
    void testRemoverTurma() {
        service.removerTurma(defaultId);
        verify(turmaRepository, times(1)).deleteById(defaultId);
    }

    @Test
    void testAdicionarEstudanteNaTurma() {
        Estudante estudante = new Estudante();
        Turma turma = new Turma();
        estudante.setTurma(new ArrayList<>());

        when(turmaRepository.findById(defaultId)).thenReturn(Optional.of(turma));

        Estudante result = service.adicionarEstudanteNaTurma(defaultId, estudante);

        assertEquals(estudante, result);
        assertTrue(result.getTurma().contains(turma));
        verify(turmaRepository, times(1)).findById(defaultId);
    }

    @Test
    void testRemoverEstudanteNaTurma() {
        Estudante estudante = new Estudante();
        Turma turma = new Turma();
        estudante.setTurma(new ArrayList<>(List.of(new Turma[]{turma})));

        when(turmaRepository.findById(defaultId)).thenReturn(Optional.of(turma));

        Estudante result = service.removerEstudanteDaTurma(defaultId, estudante);

        assertEquals(estudante, result);
        assertFalse(result.getTurma().contains(turma));
        verify(turmaRepository, times(1)).findById(defaultId);
    }
}
