package com.walgidio.challenge.dto;

import com.walgidio.challenge.domain.DefectiveEdition;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DefectiveEditionDTO {

    private Long id;

    @Valid
    @NotNull(message = "Edition is required")
    private EditionDTO edition;

    @NotBlank(message = "Defect code is required")
    private String defectCode;

    @NotEmpty(message = "Affected batches cannot be empty")
    private List<@NotBlank(message = "Batch number must not be blank") String> affectedBatches;

    public DefectiveEdition toEntity() {
        return DefectiveEdition.builder()
                .edition(edition.toEntity())
                .defectCode(defectCode)
                .affectedBatches(affectedBatches)
                .build();
    }

    public static DefectiveEditionDTO fromEntity(DefectiveEdition defectiveEdition) {
        return DefectiveEditionDTO.builder()
                .id(defectiveEdition.getId())
                .edition(EditionDTO.fromEntity(defectiveEdition.getEdition()))
                .defectCode(defectiveEdition.getDefectCode())
                .affectedBatches(defectiveEdition.getAffectedBatches())
                .build();
    }
}
