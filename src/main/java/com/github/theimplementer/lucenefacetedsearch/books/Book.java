package com.github.theimplementer.lucenefacetedsearch.books;

public class Book {

    private final String title;

    private Book(String title) {
        this.title = title;
    }

    public static Book book(String title) {
        return new Book(title);
    }

    public String getTitle() {
        return title;
    }
}
