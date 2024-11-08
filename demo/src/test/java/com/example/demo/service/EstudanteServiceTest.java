package com.example.demo.service;

import com.example.demo.database.entities.Estudante;
import com.example.demo.database.repositories.EstudanteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstudanteServiceTest {

    private final Long defaultId = 0L;
    
    @Mock
    private EstudanteRepository estudanteRepository;

    @InjectMocks
    private EstudanteService service;

    @Test
    void testCadastrarEstudante() {
        String nome = "testNome";
        String matricula = "testMatricula";

        Estudante result = service.cadastrarEstudante(nome, matricula);

        assertEquals(nome, result.getNome());
        assertEquals(matricula, result.getMatricula());
        verify(estudanteRepository, times(1)).save(any(Estudante.class));
    }

    @Test
    void testListarEstudantes() {
        service.listarEstudantes();
        verify(estudanteRepository, times(1)).findAll();
    }

    @Test
    void testBuscarEstudantePorId() {
        assertThrows(RuntimeException.class, () -> service.buscarEstudantePorId(defaultId));
        verify(estudanteRepository, times(1)).findById(defaultId);
    }

    @Test
    void testAtualizarEstudante() {
        Estudante estudante = new Estudante();
        String novoNome = "testNome";
        String novaMatricula = "testMatricula";

        when(estudanteRepository.findById(defaultId)).thenReturn(Optional.of(estudante));
        Estudante result = service.atualizarEstudante(defaultId, novoNome, novaMatricula);

        assertEquals(novoNome, result.getNome());
        assertEquals(novaMatricula, result.getMatricula());
        verify(estudanteRepository, times(1)).findById(defaultId);
    }
    
    @Test
    void testRemoverEstudante() {
        service.removerEstudante(defaultId);
        verify(estudanteRepository, times(1)).deleteById(defaultId);
    }
}
