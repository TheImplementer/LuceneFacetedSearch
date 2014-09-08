package com.github.theimplementer.lucenefacetedsearch.books;

import java.util.List;

public interface BooksRepository {
    List<Book> list() throws Exception;

    void add(Book book) throws Exception;
}
