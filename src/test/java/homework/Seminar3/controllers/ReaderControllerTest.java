package homework.Seminar3.controllers;

import homework.Seminar3.model.Book;
import homework.Seminar3.model.Issue;
import homework.Seminar3.model.Reader;
import homework.Seminar3.repository.IssuesRepository;
import homework.Seminar3.repository.ReaderRepository;
import homework.Seminar3.service.ReaderService;
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
class ReaderControllerTest extends JUnitSpringBootBase{

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ReaderService readerService;

    @Autowired
    ReaderRepository readerRepository;

    @Autowired
    IssuesRepository issuesRepository;

    @Test
    void getReaderById() {
        Reader reader = readerRepository.save(new Reader(1L,"Test Reader"));
        log.info(reader.toString());
        String uri = "/reader/"+reader.getId();
        log.info(uri);

        Reader testReader = webTestClient.get()
                .uri(uri)
                .exchange()
                .expectStatus().isFound()
                .expectBody(Reader.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(testReader);
        log.info(testReader.toString());
        Assertions.assertEquals(reader.getId(), testReader.getId());
        Assertions.assertEquals(reader.getName(), testReader.getName());
    }

    @Test
    void getIssuesByIdReader() {

        issuesRepository.saveAll(List.of(
                new Issue(1L,1L),
                new Issue(2L,2L),
                new Issue(2L,1L)));
        List<Issue> issues = issuesRepository.findByReaderIdAndReturnedTimestamp(1L, null);
        log.info(issues.toString());

        List<Issue> booksWeb = webTestClient.get()
                .uri("/reader/{}/issue")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Issue>>() {})
                .returnResult()
                .getResponseBody();
        assertEquals(booksWeb.size(),issues.size());
        for (Issue issue : booksWeb) {
            boolean found = issues.stream()
                    .filter(it -> Objects.equals(it.getBookId(), issue.getBookId()))
                    .anyMatch(it -> Objects.equals(it.getReaderId(), issue.getReaderId()));
            assertTrue(found);
        }
    }

    @Test
    void createReader() {
        Reader reader = new Reader(1L,"Test Create");
        log.info(reader.toString());

        Book readerWeb = webTestClient.post()
                .uri("/reader")
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class)
                .returnResult().getResponseBody();
        Assertions.assertNotNull(readerWeb);
        Assertions.assertNotNull(readerWeb.getId());
        Assertions.assertEquals(readerWeb.getName(), reader.getName());
        Assertions.assertTrue(readerRepository.findById(readerWeb.getId()).isPresent());
    }

    @Test
    void deleteReader() {
        Reader reader = new Reader(1L,"Test Reader");
        readerRepository.save(reader);
        log.info(reader.toString());
        webTestClient.delete()
                .uri("/reader/{}", reader.getId())
                .exchange()
                .expectStatus().isNoContent();
        assertNull(readerService.getReaderById(reader.getId()));
    }
}