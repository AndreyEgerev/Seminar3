package homework.Seminar3.controllers;

import com.sun.jdi.InvalidCodeIndexException;
import homework.Seminar3.model.Book;
import homework.Seminar3.model.Issue;
import homework.Seminar3.model.Reader;
import homework.Seminar3.repository.IssuesRepository;
import homework.Seminar3.service.IssuerService;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
class IssuerControllerTest extends JUnitSpringBootBase{

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    IssuesRepository issuesRepository;

    @Autowired
    IssuerService issuerService;

    @Test
    void issueBook() {
        IssueRequest issueRequest = new IssueRequest(1L, 1L);

        Issue issueWeb = webTestClient.post()
                .uri("/issue")
                .bodyValue(issueRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Issue.class)
                .returnResult().getResponseBody();
        assertNotNull(issueWeb);
        assertNotNull(issueWeb.getId());
        Assertions.assertEquals(issueWeb.getReaderId(), issueRequest.getReaderId());
        Assertions.assertEquals(issueWeb.getBookId(), issueRequest.getBookId());
        assertTrue(issuesRepository.findById(issueWeb.getId()).isPresent());
    }

    @Test
    void getInfoIssueById() {

        Issue issue = new Issue(1L,1L);
        issue = issuesRepository.save(issue);
        log.info(issue.toString());

        Issue issueWeb = webTestClient.post()
                .uri("/issue/" + issue.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Issue.class)
                .returnResult().getResponseBody();
        Assertions.assertNotNull(issueWeb);
        Assertions.assertEquals(issue.getId(), issueWeb.getId());
        Assertions.assertEquals(issue.getBookId(), issueWeb.getBookId());
        Assertions.assertEquals(issue.getReaderId(), issueWeb.getReaderId());
    }

    @Test
    void returnedIssue() {
        Issue issue = new Issue(1L,1L);
        issue = issuesRepository.save(issue);
        log.info(issue.toString());

        Issue issueWeb = webTestClient.put()
                .uri("/issue/" + issue.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Issue.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(issueWeb);
        Assertions.assertEquals(issue.getId(), issueWeb.getId());
        Assertions.assertEquals(issue.getBookId(), issueWeb.getBookId());
        Assertions.assertEquals(issue.getReaderId(), issueWeb.getReaderId());
        Assertions.assertNotNull(issueWeb.getReturnedTimestamp());

    }
}