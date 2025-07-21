package com.walgidio.challenge.dto;

import com.walgidio.challenge.domain.Book;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {

    private Long id;

    @NotBlank(message = "Publisher is required")
    private String publisher;

    @Min(value = 1000, message = "Year must be valid")
    private int publishedYear;

    @NotNull(message = "Batch number is required")
    private String batchNumber;

    @Valid
    @NotNull(message = "Edition is required")
    private EditionDTO edition;

    public Book toEntity() {
        return Book.builder()
                .publisher(publisher)
                .publishedYear(publishedYear)
                .batchNumber(batchNumber)
                .edition(edition.toEntity())
                .build();
    }

    public static BookDTO fromEntity(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .publisher(book.getPublisher())
                .publishedYear(book.getPublishedYear())
                .batchNumber(book.getBatchNumber())
                .edition(EditionDTO.fromEntity(book.getEdition()))
                .build();
    }
}
