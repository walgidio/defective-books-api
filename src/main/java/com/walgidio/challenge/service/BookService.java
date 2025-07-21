package com.walgidio.challenge.service;

import com.walgidio.challenge.dto.BookDTO;
import com.walgidio.challenge.domain.Book;
import com.walgidio.challenge.domain.Edition;
import com.walgidio.challenge.repository.BookRepository;
import com.walgidio.challenge.repository.EditionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final EditionRepository editionRepository;

    public BookDTO addBook(BookDTO bookDTO) {
        Edition edition = editionRepository.findById(bookDTO.getEdition().getIsbn())
                .orElseGet(() -> editionRepository.save(bookDTO.getEdition().toEntity()));

        Book book = bookDTO.toEntity();
        book.setEdition(edition);
        Book savedBook = bookRepository.save(book);
        return BookDTO.fromEntity(savedBook);
    }

    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Book not found"));
        return BookDTO.fromEntity(book);
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookDTO::fromEntity)
                .toList();
    }

    public BookDTO updateBook(Long id, BookDTO updatedBook){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Book not found"));

        Edition edition = editionRepository.findById(updatedBook.getEdition().getIsbn())
                .orElseGet(() -> editionRepository.save(updatedBook.getEdition().toEntity()));

        book.setPublisher(updatedBook.getPublisher());
        book.setPublishedYear(updatedBook.getPublishedYear());
        book.setBatchNumber(updatedBook.getBatchNumber());
        book.setEdition(edition);

        return BookDTO.fromEntity(bookRepository.save(book));
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Book not found");
        }
        bookRepository.deleteById(id);
    }
}
