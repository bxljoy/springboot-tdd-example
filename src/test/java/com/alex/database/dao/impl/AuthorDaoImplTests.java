package com.alex.database.dao.impl;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alex.database.TestDataUtil;
import com.alex.database.domain.Author;

@ExtendWith(MockitoExtension.class)
public class AuthorDaoImplTests {
    
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImpl underTest;

    @Test
    public void testThatCreateAuthorGeneratesCorrectSql() {
        Author author = TestDataUtil.createTestAuthor(1L, "Alex", 40);
        underTest.create(author);

        verify(jdbcTemplate).update(eq("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)"), 
        eq(1L), eq("Alex"), eq(40));
    }

    @Test
    public void testThatFindAuthorByIdGeneratesCorrectSql() {
        underTest.findOne(1L);

        verify(jdbcTemplate).query(eq("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1"), 
        ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any(),
        eq(1L));
    }

    @Test
    public void testThatFindManyAuthorsGeneratesCorrectSql() {
        underTest.findMany();

        verify(jdbcTemplate).query(eq("SELECT id, name, age FROM authors"), 
        ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any());
    }

    @Test
    public void testThatUpdateAuthorGeneratesCorrectSql() {
        Author author = TestDataUtil.createTestAuthor(1L, "Alex", 40);
        underTest.update(author);

        verify(jdbcTemplate).update(eq("UPDATE authors SET name = ?, age = ? WHERE id = ?"), 
        eq("Alex"), eq(40), eq(1L));
    }

    @Test
    public void testThatDeleteAuthorGeneratesCorrectSql() {
        underTest.delete(1L);

        verify(jdbcTemplate).update(eq("DELETE FROM authors WHERE id = ?"), eq(1L));
    }

}
