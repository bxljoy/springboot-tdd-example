package com.alex.database.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.alex.database.dao.AuthorDao;
import com.alex.database.domain.Author;

@Repository
public class AuthorDaoImpl implements AuthorDao {

    private final JdbcTemplate jdbcTemplate;

    public AuthorDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Author author) {
        jdbcTemplate.update("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)", author.getId(), author.getName(), author.getAge());
    }

    @Override
    public Optional<Author> findOne(Long authorId) {
        List<Author> results = jdbcTemplate.query("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1", new AuthorRowMapper(), authorId);
        return results.stream().findFirst();
    }

    @Override
    public List<Author> findMany() {
        return jdbcTemplate.query("SELECT id, name, age FROM authors", new AuthorRowMapper());
    }

    public static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Author.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .age(rs.getInt("age"))
                .build();
        }
    }

    @Override
    public void update(Author author) {
        jdbcTemplate.update("UPDATE authors SET name = ?, age = ? WHERE id = ?", author.getName(), author.getAge(), author.getId());
    }
    
    @Override
    public void delete(Long authorId) {
        jdbcTemplate.update("DELETE FROM authors WHERE id = ?", authorId);
    }
}
