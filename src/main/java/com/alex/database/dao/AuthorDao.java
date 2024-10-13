package com.alex.database.dao;

import com.alex.database.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    void create(Author author);

    Optional<Author> findOne(Long id);

    List<Author> findMany();

    void update(Author author);

    void delete(Long id);
    
}
