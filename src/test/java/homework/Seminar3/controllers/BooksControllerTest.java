package homework.Seminar3.controllers;
import homework.Seminar3.model.Book;
import homework.Seminar3.repository.BookRepository;
import homework.Seminar3.service.BookService;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
class BooksControllerTest extends JUnitSpringBootBase{

    @Autowired
    BookService bookService;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    WebTestClient webTestClient;

    @Test
    void getBookById() {

        Book book = new Book(1L,"Test");
        log.info(book.toString());

        Book newBook = bookRepository.save(book);//bookService.addBook(book);
        log.info(newBook.toString());
        Book responseBook = webTestClient.get()
                .uri("/books/" + newBook.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .returnResult()
                .getResponseBody();
        log.info(responseBook.toString());
        Assertions.assertEquals(newBook.getId(),responseBook.getId());
        Assertions.assertEquals(newBook.getName(),responseBook.getName());
    }

    @Test
    void getBook() {
        bookRepository.saveAll(List.of(new Book("Test1"), new Book("Test2")));
        List<Book> books = bookService.getAllBook();

        List<Book> booksWeb = webTestClient.get()
                .uri("/books")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Book>>() {})
                .returnResult()
                .getResponseBody();
        Assertions.assertEquals(booksWeb.size(),books.size());
        for (Book book : booksWeb) {
            boolean found = books.stream()
                    .filter(it -> Objects.equals(it.getId(), book.getId()))
                    .anyMatch(it -> Objects.equals(it.getName(), book.getName()));
            Assertions.assertTrue(found);
        }
    }

    @Test
    void createBook() {
        Book book = new Book(1L,"Test Create");

        Book bookWeb = webTestClient.post()
                .uri("/books")
                .bodyValue(book)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class)
                .returnResult().getResponseBody();
        Assertions.assertNotNull(bookWeb);
        Assertions.assertNotNull(bookWeb.getId());
        Assertions.assertEquals(bookWeb.getName(), book.getName());
        Assertions.assertTrue(bookRepository.findById(bookWeb.getId()).isPresent());
    }

    @Test
    void deleteBook() {
        log.info("Delete test");
        Book book = new Book(1L,"Test");
        book = bookRepository.save(book);
        log.info(book.toString());
        webTestClient.delete()
                .uri("/books/" + book.getId())
                .exchange()
                .expectStatus().isNoContent();
        Assertions.assertNull(bookService.getBookById(book.getId()));
    }
}