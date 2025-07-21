package com.walgidio.challenge.dto;

import com.walgidio.challenge.domain.Edition;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditionDTO {
    @NotBlank(message = "ISBN is required")
    private String isbn;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author name is required")
    private String authorName;

    @Min(value = 1, message = "Number must be at least 1")
    private int number;

    public Edition toEntity() {
        return Edition.builder()
                .isbn(isbn)
                .title(title)
                .authorName(authorName)
                .number(number)
                .build();
    }

    public static EditionDTO fromEntity(Edition edition) {
        return EditionDTO.builder()
                .isbn(edition.getIsbn())
                .title(edition.getTitle())
                .authorName(edition.getAuthorName())
                .number(edition.getNumber())
                .build();
    }
}
