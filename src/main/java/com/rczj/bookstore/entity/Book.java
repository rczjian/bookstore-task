package com.rczj.bookstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Table(name = "books")
@Entity
@Getter
@Setter
public class Book {
    @Id
    private String isbn;
    private String title;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "books_authors",
            joinColumns = { @JoinColumn(name = "book_isbn") },
            inverseJoinColumns = { @JoinColumn(name = "author_id") }
    )
    private Set<Author> authors;
    private Integer year;
    private Double price;
    private String genre;
}
