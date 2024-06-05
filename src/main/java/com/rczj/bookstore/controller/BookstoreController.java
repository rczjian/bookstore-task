package com.rczj.bookstore.controller;

import com.rczj.bookstore.entity.Book;
import com.rczj.bookstore.service.BookstoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookstoreController {
    @Autowired
    BookstoreService bookstoreService;

    @PostMapping("/book/{isbn}")
    public Book addBook(@PathVariable String isbn, @RequestBody Book book) {
        book.setIsbn(isbn);
        return bookstoreService.addBook(book);
    }

    @PatchMapping("/book/{isbn}")
    public Book updateBook(@PathVariable String isbn, @RequestBody Book book) {
        return bookstoreService.updateBook(isbn, book);
    }

    @GetMapping("/books")
    public List<Book> findBooks(@RequestParam(required = false, name = "title") String title,
                                @RequestParam(required = false, name = "author") String authorName) {
        if (title == null && authorName == null) {
            throw new RuntimeException("no parameters provided");
        }
        return bookstoreService.findBooks(title, authorName);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/book/{isbn}")
    public void deleteBook(@PathVariable String isbn) {
        bookstoreService.deleteBook(isbn);
    }

}
