package com.walgidio.challenge.controller;

import com.walgidio.challenge.dto.DefectiveEditionDTO;
import com.walgidio.challenge.dto.EditionDTO;
import com.walgidio.challenge.service.DefectiveEditionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DefectiveEditionController.class)
class DefectiveEditionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DefectiveEditionService defectiveEditionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createDefect_shouldReturnSaved() throws Exception {
        EditionDTO edition = EditionDTO.builder()
                .isbn("123")
                .title("Java")
                .authorName("Walgidio")
                .number(1)
                .build();
        DefectiveEditionDTO input = DefectiveEditionDTO.builder()
                .defectCode("DEFECT-002")
                .edition(edition)
                .affectedBatches(List.of("34-821-5678"))
                .build();
        DefectiveEditionDTO output = DefectiveEditionDTO.builder()
                .id(1L)
                .defectCode("DEFECT-002")
                .edition(edition)
                .affectedBatches(List.of("34-821-5678"))
                .build();

        when(defectiveEditionService.addDefect(any())).thenReturn(output);

        mockMvc.perform(post("/defects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getAllDefects_shouldReturnList() throws Exception {
        EditionDTO edition = new EditionDTO("123", "Java", "Walgidio", 1);
        DefectiveEditionDTO.builder().id(1L).defectCode("DEFECT-003").edition(edition).build();
        List<DefectiveEditionDTO> list = List.of(DefectiveEditionDTO.builder()
                .id(1L)
                .defectCode("DEFECT-003")
                .edition(edition)
                .build());

        when(defectiveEditionService.listAllDefects()).thenReturn(list);

        mockMvc.perform(get("/defects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].defectCode").value("DEFECT-003"));
    }
}
