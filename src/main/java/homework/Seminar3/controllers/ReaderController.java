package homework.Seminar3.controllers;

import homework.Seminar3.aspect.Timer;
import homework.Seminar3.model.Book;
import homework.Seminar3.model.Issue;
import homework.Seminar3.model.Reader;
import homework.Seminar3.service.BookService;
import homework.Seminar3.service.ReaderService;
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
@RequestMapping("/reader")
@Tag(name = "Читатели")
@Timer
public class ReaderController {

    @Autowired
    private ReaderService readerService;

    @GetMapping("/{id}")
    @Operation(summary = "Get reader by id", description = "Загружает читателя с указанным идентификатором в пути")
    @Timer
    public ResponseEntity<Reader> getReaderById(@PathVariable long id) {
        Reader reader = readerService.getReaderById(id);
        log.info(reader.toString());
    return reader != null
            ? ResponseEntity.ok(reader)
            : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/issue")
    @Operation(summary = "Get all issues by reader id", description = "Загружает список выдач книг у читателя с указанным идентификатором")
    public ResponseEntity<List<Issue>> getIssuesByIdReader(@PathVariable Long id) {
        List<Issue> readerIssue;
        readerIssue = readerService.getIssueByIdReader(id);
        return !readerIssue.isEmpty()
                ? ResponseEntity.ok(readerIssue)
                : ResponseEntity.notFound().build();

    }

    @PostMapping
    @Operation(summary = "Create new reader", description = "Создаёт нового читателя в библиотеке")
    public ResponseEntity<Reader> createReader(@RequestBody Reader reader) {
        Reader newReader = readerService.addReader(reader);
        return newReader != null
                ? new ResponseEntity<>(newReader, HttpStatus.CREATED)//ResponseEntity.ok(newReader)
                : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete reader by id", description = "Удаляет читателя с указанным идентификатором из библиотеки")
    public ResponseEntity<Void> deleteReader(@PathVariable Long id) {
        readerService.deleteReaderById(id);
        return ResponseEntity.noContent().build();
    }
}
