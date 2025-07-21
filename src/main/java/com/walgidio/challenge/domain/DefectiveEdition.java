package com.walgidio.challenge.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class DefectiveEdition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String defectCode;

    @ManyToOne
    @JoinColumn(name = "edition_isbn")
    private Edition edition;

    @ElementCollection
    private List<String> affectedBatches;
}
