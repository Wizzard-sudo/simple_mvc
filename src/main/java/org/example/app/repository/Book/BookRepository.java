package org.example.app.repository.Book;

import java.util.List;

//TODO зачем тут дженерик? Это специализированный интерфейс используйте сразу в именах Book а не item,
// и вместо дженеролизированного типа тип Book
public interface BookRepository<T> {
    List<T> retreiveAll();

    void store(T book);

    boolean removeItemById(Integer bookIdToRemove);

    boolean removeItemByAuthor(String bookAuthorToRemove);

    boolean removeItemByTitle(String bookTitleToRemove);

    boolean removeItemBySize(Integer bookSizeToRemove);

    List<T> filterItemByAuthor(String bookAuthorToFilter);

    List<T> filterItemByTitle(String bookAuthorToTitle);

    List<T> filterItemBySize(Integer bookAuthorToSize);
}
