package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository implements ProjectRepository<Book>{

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();

    @Override
    public List<Book> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        book.setId(book.hashCode());
        logger.info("store new book: " + book);
        repo.add(book);
    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        for(Book book : retreiveAll())
            if(book.getId().equals(bookIdToRemove)){
                logger.info("remove book completed: " + book);
                return repo.remove(book);
            }
        return false;
    }

    @Override
    public boolean removeItemByAuthor(String bookAuthorToRemove) {
        boolean flagRemove = false;
        for(Book book : retreiveAll())
            if(book.getAuthor().equals(bookAuthorToRemove)){
                logger.info("remove book completed: " + book);
                flagRemove = repo.remove(book);
            }
        return flagRemove;
    }

    @Override
    public boolean removeItemByTitle(String bookTitleToRemove) {
        boolean flagRemove = false;
        for(Book book : retreiveAll())
            if(book.getTitle().equals(bookTitleToRemove)){
                logger.info("remove book completed: " + book);
                flagRemove = repo.remove(book);
            }
        return flagRemove;
    }

    @Override
    public boolean removeItemBySize(Integer bookSizeToRemove) {
        boolean flagRemove = false;
        for(Book book : retreiveAll())
            if(book.getSize().equals(bookSizeToRemove)){
                logger.info("remove book completed: " + book);
                flagRemove = repo.remove(book);
            }
        return flagRemove;
    }
}
