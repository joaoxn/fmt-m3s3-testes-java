package com.example.demo.controller;

import com.example.demo.database.entities.Estudante;
import com.example.demo.database.entities.Turma;
import com.example.demo.database.repositories.TurmaRepository;
import com.example.demo.service.EstudanteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TurmaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TurmaRepository turmaRepository;

    @MockBean
    EstudanteService estudanteService;

    Turma turma;
    Estudante estudante;

    @BeforeEach
    public void setUp() {
        estudante = new Estudante();
        estudante.setId(1L);
        estudante.setNome("Jefferson");
        estudante.setMatricula("1234");

        turma = new Turma();
        turma.setNome("Estatistica");
        turma.setId(1L);

    }

    @Test
    public void adicionarEstudanteNaTurmaTest() throws Exception {
        when(turmaRepository.findById(anyLong())).thenReturn(Optional.ofNullable(turma));
        when(estudanteService.buscarEstudantePorId(anyLong())).thenReturn(estudante);

        mockMvc.perform(post("/turmas/estudantes")
                        .content("{\n" +
                                "\t\"idTurma\":1,\n" +
                                "\t\"idEstudante\":1\n" +

                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }
}