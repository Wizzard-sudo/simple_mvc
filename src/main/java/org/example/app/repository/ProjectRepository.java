package org.example.app.repository;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retreiveAll();

    void store(T book);

    boolean removeItemById(String bookIdToRemove);

    boolean removeItemByAuthor(String bookAuthorToRemove);

    boolean removeItemByTitle(String bookTitleToRemove);

    boolean removeItemBySize(Integer bookSizeToRemove);

    List<T> filterItemByAuthor(String bookAuthorToFilter);

    List<T> filterItemByTitle(String bookAuthorToTitle);

    List<T> filterItemBySize(Integer bookAuthorToSize);
}
