package com.rczj.bookstore.repository;

import com.rczj.bookstore.entity.Author;
import com.rczj.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    boolean existsBookByIsbn(String isbn);

    List<Book> findByTitle(String title);

    @Query("SELECT b from Book b where ?1 member of b.authors")
    List<Book> findByAuthor(Author author);

    @Query("SELECT b from Book b where b.title = ?1 and ?2 member of b.authors")
    List<Book> findByTitleAndAuthor(String title, Author author);
}
