package com.example.android.booklistingapp;

/**
 * Represent a book to be displayed upon user search
 * It contains a Title and an Author
 */

public class Book {

    // String value for book title
    private String bookTitle;

    // String value for book author
    private String bookAuthor;


    /**
     * constructor of object of type {@link Book}
     *
     * @param bookTitle  title of the book
     * @param bookAuthor author of the book
     */
    public Book(String bookTitle, String bookAuthor) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
    }

    /**
     * get the string value of the title of the book
     *
     * @return the title of the book
     */
    public String getBookTitle() {
        return bookTitle;
    }

    /**
     * get the string value of the author of the book
     *
     * @return the author of the book
     */
    public String getBookAuthor() {
        return bookAuthor;
    }
}
