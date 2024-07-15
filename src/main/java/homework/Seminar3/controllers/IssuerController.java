package homework.Seminar3.controllers;

import homework.Seminar3.model.Issue;
import homework.Seminar3.service.IssuerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/issue")
@Tag(name = "Выдача книг")
public class IssuerController {

  @Autowired
  private IssuerService service;


  @PostMapping
  @Operation(summary = "Create new issue", description = "Создаёт новую выдачу книги читателю")
  public ResponseEntity<Issue> issueBook(@RequestBody IssueRequest request) {
    log.info("Получен запрос на выдачу: readerId = {}, bookId = {}", request.getReaderId(), request.getBookId());
    Issue issue;
    try {
      issue = service.issue(request);
      log.info(issue.toString());
    } catch (NoSuchElementException e) {
      log.info("404");
      return ResponseEntity.notFound().build();
    } catch (RuntimeException e) {
      log.info("409");
      return new ResponseEntity<>(HttpStatusCode.valueOf(409));
    }
      log.info("201 {}", issue);
    return ResponseEntity.status(HttpStatus.CREATED).body(issue);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get issue by id", description = "Загружает факт выдачи с указанным идентификатором в пути")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Found the issue",
                  content = {@Content(mediaType = "application/json",
                          schema = @Schema(implementation = Issue.class))}),
          @ApiResponse(responseCode = "404", description = "Issue not found",
                  content = @Content)
  })
  public ResponseEntity<Issue> getInfoIssueById(@PathVariable Long id) {
    final Issue issue;
    issue = service.getInfoById(id);
    return issue != null
            ? ResponseEntity.ok(issue)
            : ResponseEntity.notFound().build();
  }

  @PutMapping("/{id}")
  @Operation(summary = "Return book by issue id", description = "Оформляет возврат книги с указанным идентификатором выдачи")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Return the book by issue id",
                  content = {@Content(mediaType = "application/json",
                          schema = @Schema(implementation = Issue.class))}),
          @ApiResponse(responseCode = "409", description = "Wrong ID Book for returning / Book not found",
                  content = @Content)
  })
  public ResponseEntity<Issue> returnedIssue(@PathVariable Long id) {
    Issue reurnedIssue = service.returnedIssue(id);
    return reurnedIssue != null
            ? ResponseEntity.ok(reurnedIssue)
            : ResponseEntity.badRequest().build();
  }

}
