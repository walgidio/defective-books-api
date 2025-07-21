package com.walgidio.challenge.service;

import com.walgidio.challenge.domain.DefectiveEdition;
import com.walgidio.challenge.domain.Edition;
import com.walgidio.challenge.dto.DefectiveEditionDTO;
import com.walgidio.challenge.dto.EditionDTO;
import com.walgidio.challenge.repository.DefectiveEditionRepository;
import com.walgidio.challenge.repository.EditionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DefectiveEditionServiceTest {

    @Mock
    private DefectiveEditionRepository defectiveEditionRepository;

    @Mock
    private EditionRepository editionRepository;

    @InjectMocks
    private DefectiveEditionService defectiveEditionService;

    private Edition edition;
    private EditionDTO editionDTO;
    private DefectiveEdition defectiveEdition;
    private DefectiveEditionDTO defectiveEditionDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        edition = Edition.builder()
                .isbn("123-456")
                .authorName("Walgidio")
                .title("Spring Boot Essentials")
                .number(1)
                .build();

        editionDTO = EditionDTO.builder()
                .isbn("123-456")
                .authorName("Walgidio")
                .title("Spring Boot Essentials")
                .number(1)
                .build();

        defectiveEdition = DefectiveEdition.builder()
                .id(1L)
                .defectCode("DEFECT-001")
                .edition(edition)
                .affectedBatches(List.of("BATCH-001", "BATCH-002"))
                .build();

        defectiveEditionDTO = DefectiveEditionDTO.builder()
                .defectCode("DEFECT-001")
                .edition(editionDTO)
                .affectedBatches(List.of("BATCH-001", "BATCH-002"))
                .build();
    }

    @Test
    @DisplayName("addDefect should save new defect with existing edition")
    void addDefect_shouldSaveDefectWithEdition() {
        when(editionRepository.findById("123-456")).thenReturn(Optional.of(edition));
        when(defectiveEditionRepository.save(any())).thenReturn(defectiveEdition);

        DefectiveEditionDTO result = defectiveEditionService.addDefect(defectiveEditionDTO);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getDefectCode()).isEqualTo("DEFECT-001");
        Assertions.assertThat(result.getEdition().getIsbn()).isEqualTo("123-456");

        verify(editionRepository).findById("123-456");
        verify(defectiveEditionRepository).save(any());
    }

    @Test
    @DisplayName("addDefect should throw exception when edition not found")
    void addDefect_shouldThrow_whenEditionNotFound() {
        when(editionRepository.findById("123-456")).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> defectiveEditionService.addDefect(defectiveEditionDTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Edition not found");

        verify(defectiveEditionRepository, never()).save(any());
    }

    @Test
    @DisplayName("listAllDefects should return list of defects")
    void listAllDefects_shouldReturnList() {
        when(defectiveEditionRepository.findAll()).thenReturn(List.of(defectiveEdition));

        List<DefectiveEditionDTO> defects = defectiveEditionService.listAllDefects();

        Assertions.assertThat(defects).hasSize(1);
        Assertions.assertThat(defects.get(0).getDefectCode()).isEqualTo("DEFECT-001");
    }
}
