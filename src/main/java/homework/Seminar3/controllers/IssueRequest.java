package homework.Seminar3.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Запрос на выдачу
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class IssueRequest {

  /**
   * Идентификатор читателя
   */
  private long readerId;

  /**
   * Идентификатор книги
   */
  private long bookId;

}
