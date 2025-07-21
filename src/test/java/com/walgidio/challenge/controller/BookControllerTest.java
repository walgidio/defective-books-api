package com.walgidio.challenge.controller;
import com.walgidio.challenge.dto.BookDTO;
import com.walgidio.challenge.dto.EditionDTO;
import com.walgidio.challenge.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
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

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("createBook should return created book")
    void createBook_shouldReturnCreatedBook() throws Exception {
        EditionDTO editionDTO = new EditionDTO("123-456", "Java", "Walgidio", 1);
        BookDTO input = new BookDTO(null, "Konoha", 2020, "Batch-1", editionDTO);
        BookDTO output = new BookDTO(1L, "Konoha", 2020, "Batch-2", editionDTO);

        when(bookService.addBook(any())).thenReturn(output);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("getAllBooks should return list of books")
    void getAllBooks_shouldReturnList() throws Exception {
        EditionDTO edition = new EditionDTO("123", "Java", "Walgidio", 1);
        List<BookDTO> books = List.of(new BookDTO(1L, "Konoha", 2020, "L1", edition));
        when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @DisplayName("getBookById should return book when successful")
    void getBookById_shouldReturnBook() throws Exception {
        EditionDTO edition = new EditionDTO("123", "Java", "Walgidio", 1);
        BookDTO book = new BookDTO(1L, "Konoha", 2020, "L1", edition);
        when(bookService.getBookById(1L)).thenReturn(book);

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("updateBook should return updated book")
    void updateBook_shouldReturnUpdated() throws Exception {
        EditionDTO edition = new EditionDTO("123", "Java", "Walgidio", 1);
        BookDTO updated = new BookDTO(1L, "Nova", 2023, "L2", edition);

        when(bookService.updateBook(eq(1L), any())).thenReturn(updated);

        mockMvc.perform(put("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.batchNumber").value("L2"));
    }

    @Test
    @DisplayName("deleteBook should return NoContent")
    void deleteBook_shouldReturnNoContent() throws Exception {
        doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNoContent());
    }
}

