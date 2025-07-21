package com.walgidio.challenge.service;

import com.walgidio.challenge.dto.DefectiveEditionDTO;
import com.walgidio.challenge.domain.DefectiveEdition;
import com.walgidio.challenge.domain.Edition;
import com.walgidio.challenge.repository.DefectiveEditionRepository;
import com.walgidio.challenge.repository.EditionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefectiveEditionService {
    private final DefectiveEditionRepository defectiveEditionRepository;
    private final EditionRepository editionRepository;

    public DefectiveEditionDTO addDefect(DefectiveEditionDTO defectiveEditionDTO) {
        Edition edition = editionRepository.findById(defectiveEditionDTO.getEdition().getIsbn())
                .orElseThrow(() -> new EntityNotFoundException("Edition not found"));

        DefectiveEdition entity = defectiveEditionDTO.toEntity();
        entity.setEdition(edition);

        DefectiveEdition saved = defectiveEditionRepository.save(entity);
        return DefectiveEditionDTO.fromEntity(saved);
    }

    public List<DefectiveEditionDTO> listAllDefects() {
        return defectiveEditionRepository.findAll().stream()
                .map(DefectiveEditionDTO::fromEntity)
                .toList();
    }
}
