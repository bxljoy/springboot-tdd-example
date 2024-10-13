package com.alex.database.dao.impl;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.ArgumentMatchers;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alex.database.TestDataUtil;
import com.alex.database.domain.Book;

@ExtendWith(MockitoExtension.class)
public class BookDaoImplTests {
    
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImpl underTest;

    @Test
    public void testThatCreateBookGeneratesCorrectSql() {
        Book book = TestDataUtil.createTestBook("1234567890", "Java", 1L);
        
        underTest.create(book);

        verify(jdbcTemplate).update(eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"),eq("1234567890"), eq("Java"), eq(1L));
    }

    @Test
    public void testThatFindBookByIsbnGeneratesCorrectSql() {
        underTest.findOne("1234567890");

        verify(jdbcTemplate).query(eq("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1"), 
        ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),
        eq("1234567890"));
    }

    @Test
    public void testThatFindManyBooksGeneratesCorrectsql() {
        underTest.findMany();

        verify(jdbcTemplate).query(eq("SELECT isbn, title, author_id FROM books"), 
        ArgumentMatchers.<BookDaoImpl.BookRowMapper>any());
    }

    @Test
    public void testThatUpdateBookGeneratesCorrectSql() {
        Book book = TestDataUtil.createTestBook("1234567890", "Java", 1L);
        underTest.update(book);

        verify(jdbcTemplate).update(eq("UPDATE books SET title = ?, author_id = ? WHERE isbn = ?"), 
        eq("Java"), eq(1L), eq("1234567890"));
    }

    @Test
    public void testThatDeleteBookGeneratesCorrectsql() {
        Book book = TestDataUtil.createTestBook("1234567890", "Java", 1L);
        underTest.delete(book.getIsbn());

        verify(jdbcTemplate).update(eq("DELETE FROM books WHERE isbn = ?"), eq("1234567890"));

    }

}
