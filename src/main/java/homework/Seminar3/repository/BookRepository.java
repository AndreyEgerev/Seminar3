package homework.Seminar3.repository;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import homework.Seminar3.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//@Data
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

//  private static final Logger log = LoggerFactory.getLogger(BookRepository.class);
//  private final List<Book> books;
//
//  public BookRepository() {
//    this.books = new ArrayList<>();
//  }
//
//  @PostConstruct
//  public void generateData() {
//    books.addAll(List.of(
//      new Book("война и мир"),
//      new Book("метрвые души"),
//      new Book("чистый код")
//    ));
//  }
//
//  public Book getBookById(long id) {
//    return books.stream().filter(it -> Objects.equals(it.getId(), id))
//      .findFirst()
//      .orElse(null);
//  }
//
//  public void deleteBook(Long id) {
//    books.removeIf(book -> book.getId().equals(id));
//  }
//
//  public Book addBook (Book book) {
//    if (books.stream().anyMatch(existBook -> //existBook.getId().equals(book.getId()) ||
//                                          existBook.getName().equals(book.getName()))) {
//      return null;
//    } else {
//      Book newBook = new Book(book.getName());
//      books.add(newBook);
//      return newBook;
//    }
//  }
//
//  public Book updateBook(Long id, Book updatebook) {
//    return books.stream()
//            .filter(book -> book.getId().equals(id))
//            .peek(book -> {book.setName(updatebook.getName());})
//            .findFirst()
//            .orElse(null);
//  }
//  public List<Book> getAllBook() {
//    return List.copyOf(books);
//  }

}
