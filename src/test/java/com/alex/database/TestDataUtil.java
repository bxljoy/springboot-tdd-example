package com.alex.database;

import com.alex.database.domain.Author;
import com.alex.database.domain.Book;

public final class TestDataUtil {
    
    private TestDataUtil() {
    }

    public static Author createTestAuthor(Long id, String name, Integer age) {
        return Author.builder()
            .id(id)
            .name(name)
            .age(age)
            .build();
    }

    public static Book createTestBook(String isbn, String title, Long authorId) {
        return Book.builder()
            .isbn(isbn)
            .title(title)
            .authorId(authorId)
            .build();
    }
}
