package com.alex.database.dao.impl;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.alex.database.TestDataUtil;
import com.alex.database.domain.Author;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorDaoImplIntegrationTests {

    private AuthorDaoImpl underTest;

    @Autowired
    public AuthorDaoImplIntegrationTests(AuthorDaoImpl underTest) {
        this.underTest = underTest;
    }
    
    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthor(1L, "Alex", 40);
        underTest.create(author);
        Optional<Author> result = underTest.findOne(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatMultiAuthorsCanBeCreatedAndRecalled() {
        Author authorA = TestDataUtil.createTestAuthor(1L, "Jess", 40);
        underTest.create(authorA);
        Author authorB = TestDataUtil.createTestAuthor(3L, "John", 50);
        underTest.create(authorB);
        List<Author> result = underTest.findMany();
        assertThat(result).hasSize(2)
        .contains(authorA, authorB)
        .doesNotContain(TestDataUtil.createTestAuthor(4L, "Jane", 30));
    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        Author author = TestDataUtil.createTestAuthor(1L, "Alex", 40);
        underTest.create(author);
        Author updatedAuthor = TestDataUtil.createTestAuthor(1L, "Jess", 41);
        underTest.update(updatedAuthor);
        Optional<Author> result = underTest.findOne(author.getId());
        assertThat(result).isPresent().contains(updatedAuthor);
        assertThat(result.get()).isEqualTo(updatedAuthor);
    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        Author author = TestDataUtil.createTestAuthor(1L, "Alex", 40);
        underTest.create(author);

        Optional<Author> result = underTest.findOne(author.getId());
        assertThat(result).isPresent().contains(author);
        assertThat(result.get()).isEqualTo(author);

        underTest.delete(author.getId());

        Optional<Author> resultB = underTest.findOne(author.getId());
        assertThat(resultB).isEmpty();
    }
}
