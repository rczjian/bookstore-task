package com.rczj.bookstore.repository;

import com.rczj.bookstore.entity.Author;
import com.rczj.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author,String> {

    Optional<Author> findByNameAndBirthday(String name, Date birthday);

    Optional<Author> findByName(String name);
}
