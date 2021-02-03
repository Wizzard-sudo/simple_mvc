package org.example.app.repository;

import java.util.List;

public interface BookRepository<Book> {
    List<Book> retreiveAll();

    void store(Book book);

    boolean removeItemById(Integer bookIdToRemove);

    boolean removeItemByAuthor(String bookAuthorToRemove);

    boolean removeItemByTitle(String bookTitleToRemove);

    boolean removeItemBySize(Integer bookSizeToRemove);

    List<Book> filterItemByAuthor(String bookAuthorToFilter);

    List<Book> filterItemByTitle(String bookAuthorToTitle);

    List<Book> filterItemBySize(Integer bookAuthorToSize);
}
