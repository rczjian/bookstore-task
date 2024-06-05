package com.rczj.bookstore.service;

import com.rczj.bookstore.entity.Author;
import com.rczj.bookstore.entity.Book;
import com.rczj.bookstore.repository.AuthorRepository;
import com.rczj.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookstoreService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    public Book addBook(Book book) {
        if (bookRepository.existsBookByIsbn(book.getIsbn())) {
            throw new RuntimeException("book exists");
        }
        Set<Author> authors = new HashSet<>();
        for (Author author : book.getAuthors()) {
            Optional<Author> optionalAuthor = authorRepository.findByNameAndBirthday(author.getName(), author.getBirthday());
            if (optionalAuthor.isEmpty()) {
                authors.add(authorRepository.saveAndFlush(author));
            } else {
                authors.add(optionalAuthor.get());
            }
        }
        book.setAuthors(authors);
        return bookRepository.saveAndFlush(book);
    }

    public Book updateBook(String isbn, Book updatedBook) {
        Optional<Book> optionalBook = bookRepository.findById(isbn);
        Book book;
        if (optionalBook.isEmpty()) {
            throw new RuntimeException("book not found");
        }
        book = optionalBook.get();
        if (updatedBook.getTitle() != null) {
            book.setTitle(updatedBook.getTitle());
        }
        if (updatedBook.getAuthors() != null) {
            Set<Author> authors = new HashSet<>();
            for (Author author : updatedBook.getAuthors()) {
                Optional<Author> optionalAuthor = authorRepository.findByNameAndBirthday(author.getName(), author.getBirthday());
                if (optionalAuthor.isEmpty()) {
                    authors.add(authorRepository.saveAndFlush(author));
                } else {
                    authors.add(optionalAuthor.get());
                }
            }
            book.setAuthors(authors);
        }
        if (updatedBook.getYear() != null) {
            book.setYear(updatedBook.getYear());
        }
        if (updatedBook.getPrice() != null) {
            book.setPrice(updatedBook.getPrice());
        }
        if (updatedBook.getGenre() != null) {
            book.setGenre(updatedBook.getGenre());
        }
        return bookRepository.saveAndFlush(book);
    }

    public List<Book> findBooks(String title, String authorName) {
        if (authorName == null) {
            return bookRepository.findByTitle(title);
        }

        Optional<Author> optionalAuthor = authorRepository.findByName(authorName);
        if (optionalAuthor.isEmpty()) {
            throw new RuntimeException("no author found with given name");
        }
        Author author = optionalAuthor.get();
        if (title == null) {
            return bookRepository.findByAuthor(author);
        }
        return bookRepository.findByTitleAndAuthor(title, author);
    }

    public void deleteBook(String isbn) {
        bookRepository.deleteById(isbn);
    }
}
