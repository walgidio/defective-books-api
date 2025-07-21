package com.walgidio.challenge.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String publisher;
    private int publishedYear;
    private String batchNumber;

    @ManyToOne
    @JoinColumn(name = "edition_isbn", referencedColumnName = "isbn")
    private Edition edition;
}
