package homework.Seminar3.controllers;

import homework.Seminar3.model.Book;
import homework.Seminar3.model.Issue;
import homework.Seminar3.model.Reader;
import homework.Seminar3.repository.IssuesRepository;
import homework.Seminar3.service.IssuerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

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
    }

    @Test
    void returnedIssue() {

    }
}