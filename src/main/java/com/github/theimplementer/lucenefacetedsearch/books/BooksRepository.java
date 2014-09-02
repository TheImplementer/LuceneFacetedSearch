package com.github.theimplementer.lucenefacetedsearch.books;

import java.io.IOException;
import java.util.List;

public interface BooksRepository {
    List<Book> list() throws IOException;
}
