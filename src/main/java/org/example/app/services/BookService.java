package org.example.app.services;

import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final ProjectRepository<Book> bookRepo;

    @Autowired
    public BookService(ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retreiveAll();
    }

    public void saveBook(Book book) {
        bookRepo.store(book);
    }

    public boolean removeBookById(Integer bookIdToRemove) {
        return bookRepo.removeItemById(bookIdToRemove);
    }

    public boolean removeBookByAuthor(String bookAuthorToRemove) {
        return bookRepo.removeItemByAuthor(bookAuthorToRemove);
    }

    public boolean removeBookByTitle(String bookTitleToRemove) {
        return bookRepo.removeItemByTitle(bookTitleToRemove);
    }

    public boolean removeBookBySize(Integer bookSizeToRemove) {
        return bookRepo.removeItemBySize(bookSizeToRemove);
    }

    public List<Book> getBooksByAuthor(String bookAuthorToFilter){
        return bookRepo.filterItemByAuthor(bookAuthorToFilter);
    }

    public List<Book> getBooksByTitle(String bookAuthorToTitle){
        return bookRepo.filterItemByTitle(bookAuthorToTitle);
    }

    public List<Book> getBooksBySize(Integer bookAuthorToSize){
        return bookRepo.filterItemBySize(bookAuthorToSize);
    }
}
