package com.bookstore.senla.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.senla.model.BookList;

@Repository
public interface BookRepository extends JpaRepository<BookList, Long> {
    List<BookList> findByTitle(String title);

}