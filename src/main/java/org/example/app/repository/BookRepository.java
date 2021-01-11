package org.example.app.repository;

import org.apache.log4j.Logger;
import org.example.app.services.IdProvider;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();
    private ApplicationContext context;

    @Override
    public List<Book> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        book.setId(context.getBean(IdProvider.class).provideId(book));
        logger.info("store new book: " + book);
        repo.add(book);
    }

    @Override
    public boolean removeItemById(String bookIdToRemove) {
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

    @Override
    public List<Book> filterItemByAuthor(String bookAuthorToFilter) {
        List<Book> filterBooks = new ArrayList<>();
        for(Book book : retreiveAll())
            if(book.getAuthor().contains(bookAuthorToFilter))
                filterBooks.add(book);
        return filterBooks;
    }

    @Override
    public List<Book> filterItemByTitle(String bookAuthorToTitle) {
        List<Book> filterBooks = new ArrayList<>();
        for(Book book : retreiveAll())
            if(book.getTitle().contains(bookAuthorToTitle))
                filterBooks.add(book);
        return filterBooks;
    }

    @Override
    public List<Book> filterItemBySize(Integer bookAuthorToSize) {
        List<Book> filterBooks = new ArrayList<>();
        for(Book book : retreiveAll())
            if(book.getSize().toString().contains(bookAuthorToSize.toString()))
                filterBooks.add(book);
        return filterBooks;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    private void defaultInit(){
        logger.info("default INIT in book repo bean");
    }

    private void defaultDestroy(){
        logger.info("default DESTROY in book repo bean");
    }
}
