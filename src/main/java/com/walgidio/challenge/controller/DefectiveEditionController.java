package com.walgidio.challenge.controller;

import com.walgidio.challenge.dto.DefectiveEditionDTO;
import com.walgidio.challenge.service.DefectiveEditionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/defects")
@RequiredArgsConstructor
public class DefectiveEditionController {
    private final DefectiveEditionService defectiveEditionService;

    @PostMapping
    public ResponseEntity<DefectiveEditionDTO> createDefect(@RequestBody @Valid DefectiveEditionDTO defectiveEditionDTO) {
        return ResponseEntity.ok(defectiveEditionService.addDefect(defectiveEditionDTO));
    }

    @GetMapping
    public ResponseEntity<List<DefectiveEditionDTO>> getAllDefects() {
        return ResponseEntity.ok(defectiveEditionService.listAllDefects());
    }
}
