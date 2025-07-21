package com.walgidio.challenge.service;

import com.walgidio.challenge.domain.Book;
import com.walgidio.challenge.domain.Edition;
import com.walgidio.challenge.dto.BookDTO;
import com.walgidio.challenge.dto.EditionDTO;
import com.walgidio.challenge.repository.BookRepository;
import com.walgidio.challenge.repository.EditionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private EditionRepository editionRepository;

    @InjectMocks
    private BookService bookService;

    private EditionDTO editionDTO;
    private Edition edition;
    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        editionDTO = EditionDTO.builder()
                .isbn("123-456")
                .authorName("Walgidio")
                .title("Spring Boot Essentials")
                .number(1)
                .build();

        edition = Edition.builder()
                .isbn("123-456")
                .authorName("Walgidio")
                .title("Spring Boot Essentials")
                .number(1)
                .build();

        book = Book.builder()
                .id(1L)
                .publisher("Konoha")
                .publishedYear(2025)
                .batchNumber("BATCH-001")
                .edition(edition)
                .build();
    }

    @Test
    @DisplayName("addBook should save new book with existing edition")
    void addBook_shouldSaveBookWithExistingEdition() {
        BookDTO bookDTO = BookDTO.builder()
                .publisher(book.getPublisher())
                .publishedYear(book.getPublishedYear())
                .batchNumber(book.getBatchNumber())
                .edition(editionDTO)
                .build();

        when(editionRepository.findById("123-456")).thenReturn(Optional.of(edition));
        when(bookRepository.save(any())).thenReturn(book);

        BookDTO result = bookService.addBook(BookDTO.fromEntity(book));

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getPublisher()).isEqualTo("Konoha");
        Assertions.assertThat(result.getEdition().getIsbn()).isEqualTo("123-456");

        verify(editionRepository, never()).save(any());
        verify(bookRepository).save(any());
    }

    @Test
    @DisplayName("getBookById should return book when found")
    void getBookById_shouldReturnBook_whenFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookDTO result = bookService.getBookById(1L);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(1L);
        Assertions.assertThat(result.getPublisher()).isEqualTo("Konoha");
    }

    @Test
    @DisplayName("getBookById should throw exception when book not found")
    void getBookById_shouldThrow_whenNotFound() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> bookService.getBookById(99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("404");
    }

    @Test
    @DisplayName("getAllBooks should return list of book")
    void getAllBooks_shouldReturnList() {
        when(bookRepository.findAll()).thenReturn(List.of(book));

        List<BookDTO> books = bookService.getAllBooks();

        Assertions.assertThat(books).hasSize(1);
        Assertions.assertThat(books.get(0).getEdition().getIsbn()).isEqualTo("123-456");
    }

    @Test
    @DisplayName("updateBook should update existing book")
    void updateBook_shouldUpdateExistingBook() {
        BookDTO updateDTO = BookDTO.builder()
                .publisher("NewPub")
                .publishedYear(2025)
                .batchNumber("B999")
                .edition(editionDTO)
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(editionRepository.findById("123-456")).thenReturn(Optional.of(edition));
        when(bookRepository.save(any())).thenReturn(book);

        BookDTO updated = bookService.updateBook(1L, updateDTO);

        Assertions.assertThat(updated.getPublisher()).isEqualTo("NewPub");
        verify(bookRepository).save(any());
    }

    @Test
    @DisplayName("updateBook should throw exception when book not found")
    void updateBook_shouldThrow_whenBookNotFound() {
        BookDTO updateDTO = BookDTO.builder()
                .publisher("NewPub")
                .publishedYear(2025)
                .batchNumber("B999")
                .edition(editionDTO)
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> bookService.updateBook(1L, updateDTO))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("404");
    }

    @Test
    @DisplayName("deleteBook should delete existing book")
    void deleteBook_shouldDelete_whenExists() {
        when(bookRepository.existsById(1L)).thenReturn(true);

        bookService.deleteBook(1L);

        verify(bookRepository).deleteById(1L);
    }

    @Test
    @DisplayName("deleteBook should throw exception when book not exists")
    void deleteBook_shouldThrow_whenNotExists() {
        when(bookRepository.existsById(2L)).thenReturn(false);

        Assertions.assertThatThrownBy(() -> bookService.deleteBook(2L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("404");
    }

}