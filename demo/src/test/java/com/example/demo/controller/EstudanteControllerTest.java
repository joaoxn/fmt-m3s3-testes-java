package com.example.demo.controller;

import com.example.demo.database.entities.Estudante;
import com.example.demo.service.EstudanteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EstudanteController.class)
@AutoConfigureMockMvc
class EstudanteControllerTest {

    private Estudante estudante;

    @MockBean
    private EstudanteService service;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        estudante = new Estudante(0L, "testNome", "testMatricula", Collections.emptyList());
    }

    @Test
    void listarEstudantes() throws Exception {
        when(service.listarEstudantes()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/estudantes"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarEstudantePorId() throws Exception {
        when(service.buscarEstudantePorId(any(Long.class))).thenReturn(estudante);

        mockMvc.perform(
                        get("/estudantes/{id}",1L))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void cadastrarEstudante() throws Exception {
        when(service.cadastrarEstudante(estudante.getNome(), estudante.getMatricula())).thenReturn(estudante);

        mockMvc.perform(
                        post("/estudantes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                        "\t\"nome\":\""+ estudante.getNome()+"\",\n" +
                                        "\t\"matricula\": \""+ estudante.getMatricula()+"\"\n" +
                                        "}")
                )
                .andExpect(status().isOk());
    }

    @Test
    void atualizarEstudante() throws Exception {
        when(service.buscarEstudantePorId(any(Long.class))).thenReturn(estudante);

        var estudanteTeste = "Rodrigo";

        mockMvc.perform(
                        put("/estudantes/{id}",1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                        "\t\"nome\":\""+ estudanteTeste +"\",\n" +
                                        "\t\"matricula\": \""+ estudante.getMatricula()+"\"\n" +
                                        "}")
                )
                .andExpect(status().isOk());
    }

    @Test
    void removerEstudante() throws Exception {
        mockMvc.perform(
                        delete("/estudantes/{id}",1L))
                .andExpect(status().isNoContent());
    }
}