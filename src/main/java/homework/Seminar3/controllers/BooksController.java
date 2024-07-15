package homework.Seminar3.controllers;

import homework.Seminar3.model.Book;
import homework.Seminar3.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/books")
@Tag(name = "Книги")
public class BooksController {

    @Autowired
    private BookService bookService;

    @GetMapping("/{id}")
    @Operation(summary = "Get book by id", description = "Загружает книгу с указанным идентификатором в пути")
    public ResponseEntity<Book> getBookById(@PathVariable long id) {
        Book book = bookService.getBookById(id);
        log.info(book != null ? book.toString() : "none");
    return book != null
            ? ResponseEntity.ok(book)
            : ResponseEntity.notFound().build();
    }
    @GetMapping
    @RequestMapping(method = RequestMethod.GET)
    @Operation(summary = "Get all books", description = "Загружает список всех книг, которые есть в библиотеке")
    public ResponseEntity<List<Book>> getBook() {
        List<Book> book = bookService.getAllBook();
        log.info(!book.isEmpty() ? book.toString() : "none");
        return !book.isEmpty()
                ? ResponseEntity.ok(book)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    @RequestMapping(method = RequestMethod.POST)
    @Operation(summary = "Create new book", description = "Создаёт новую книгу в библиотеке")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book newBook = bookService.addBook(book);
        log.info(newBook != null ? newBook.toString() : "none");
        return newBook != null
                ? new ResponseEntity<>(newBook, HttpStatus.CREATED)//ResponseEntity.ok(newBook)
                : ResponseEntity.badRequest().build();
        //new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
//    @RequestMapping(method = RequestMethod.DELETE)
    @Operation(summary = "Delete book by id", description = "Удаляет книгу с указанным идентификатором из библиотеки")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }

}
