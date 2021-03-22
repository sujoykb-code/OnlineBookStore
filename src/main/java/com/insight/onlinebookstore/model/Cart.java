package com.insight.onlinebookstore.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jimy on 22/03/2021.
 */
public class Cart {

    private List<Book> books;

    public Cart() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> getBooks() {
        return books;
    }

}
