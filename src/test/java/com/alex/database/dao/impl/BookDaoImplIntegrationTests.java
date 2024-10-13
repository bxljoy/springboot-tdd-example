package com.alex.database.dao.impl;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.alex.database.TestDataUtil;
import com.alex.database.dao.AuthorDao;
import com.alex.database.domain.Author;
import com.alex.database.domain.Book;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDaoImplIntegrationTests {
    
    private BookDaoImpl underTest;

    private AuthorDao authorDao;

    @Autowired
    public BookDaoImplIntegrationTests(BookDaoImpl underTest, AuthorDao authorDao) {
        this.underTest = underTest;
        this.authorDao = authorDao;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthor(1L, "Jess", 40);
        authorDao.create(author);

        Book book = TestDataUtil.createTestBook("1234567890", "Java", author.getId());
        underTest.create(book);
        Optional<Book> result = underTest.findOne(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatMultiBooksCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthor(1L, "Dora", 7);
        authorDao.create(author);

        Book bookA = TestDataUtil.createTestBook("1234567890", "Java", author.getId());
        underTest.create(bookA);
        Book bookB = TestDataUtil.createTestBook("0987654321", "Spring", author.getId());
        underTest.create(bookB);
        assertThat(underTest.findMany()).hasSize(2)
        .contains(bookA, bookB)
        .doesNotContain(TestDataUtil.createTestBook("47293749234", "Rust", author.getId()));
    }

    @Test
    public void testThatBookCanBeUpdated() {
        Author author = TestDataUtil.createTestAuthor(1L, "Jess", 40);
        authorDao.create(author);

        Book book = TestDataUtil.createTestBook("1234567890", "Java", author.getId());
        underTest.create(book);
        Book updatedBook = TestDataUtil.createTestBook("1234567890", "Spring", author.getId());
        underTest.update(updatedBook);
        Optional<Book> result = underTest.findOne(book.getIsbn());
        assertThat(result).isPresent().contains(updatedBook);
        assertThat(result.get()).isEqualTo(updatedBook);
    }

    @Test
    public void testThatBookCanBeDeleted() {
        Author author = TestDataUtil.createTestAuthor(1L, "Jess", 40);
        authorDao.create(author);

        Book book = TestDataUtil.createTestBook("1234567890", "Java", author.getId());
        underTest.create(book);

        Optional<Book> result = underTest.findOne(book.getIsbn());
        assertThat(result).isPresent().contains(book);
        assertThat(result.get()).isEqualTo(book);

        underTest.delete(book.getIsbn());
        Optional<Book> resultB = underTest.findOne(book.getIsbn());
        assertThat(resultB).isEmpty();
    }
}
