package com.alex.database.dao;

import com.alex.database.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    
    void create(Book book);

    Optional<Book> findOne(String isbn);

    List<Book> findMany();

    void update(Book book);

    void delete(String isbn);
}
