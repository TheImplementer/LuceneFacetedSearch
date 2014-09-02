package com.github.theimplementer.lucenefacetedsearch.books;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.awt.print.Book;
import java.util.List;

@Path("books")
public class BooksResource {

    private final BooksRepository booksRepository;

    @Inject
    public BooksResource(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @GET
    @Path("list")
    public List<Book> list() {
        return null;
    }
}
