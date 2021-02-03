package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.exceptions.NonexistentBookException;
import org.example.app.services.BookService;
import org.example.app.services.FileService;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.*;
import java.util.List;

@Controller
@RequestMapping(value = "books")
@Scope("singleton")
public class BookShelfController {

    private final Logger logger = Logger.getLogger(BookShelfController.class);
    private final BookService bookService;
    private final FileService fileService;

    @Autowired
    public BookShelfController(BookService bookService, FileService fileService) {
        this.bookService = bookService;
        this.fileService = fileService;
    }

    @GetMapping("/shelf")
    public String books(Model model){
        logger.info(this.toString());
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks());
        model.addAttribute("fileList", fileService.getFilesName());

        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("book", book);
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("fileList", fileService.getFilesName());
            return "book_shelf";
        }else{
                bookService.saveBook(book);
                logger.info("current repository size: " + bookService.getAllBooks().size());
            }
            return "redirect:/books/shelf";
        }

    @PostMapping("/remove/id")
    public String removeBookById(@RequestParam(name = "bookIdToRemove") String bookIdToRemove) throws NonexistentBookException {
        if(bookIdToRemove.isEmpty()){
            throw new NonexistentBookException("errorMessageRemoveBookById", "id empty!");
        }else{
            if(bookService.removeBookById(Integer.valueOf(bookIdToRemove))){
                return "redirect:/books/shelf";
            }else{
                logger.info("exception remove book by id");
                throw new NonexistentBookException("errorMessageRemoveBookById", "id does not exist");
            }
        }
    }

    @PostMapping("/remove/author")
    public String removeBookByAuthor (@RequestParam(name = "bookAuthorToRemove") String bookAuthorToRemove) throws NonexistentBookException {
        if(bookAuthorToRemove.isEmpty()){
            throw new NonexistentBookException("errorMessageRemoveBookByAuthor", "author empty!");
        }else {
            if(bookService.removeBookByAuthor(bookAuthorToRemove))
                return "redirect:/books/shelf";
            else{
                logger.info("exception remove book by author");
                throw new NonexistentBookException("errorMessageRemoveBookByAuthor", "author does not exist");
            }
        }
    }

    @PostMapping("/remove/title")
    public String removeBookByTitle(@RequestParam(name = "bookTitleToRemove") String bookTitleToRemove) throws NonexistentBookException {
        if(bookTitleToRemove.isEmpty()){
            throw new NonexistentBookException("errorMessageRemoveBookByTitle", "title empty!");
        }else {
            if(bookService.removeBookByTitle(bookTitleToRemove))
                return "redirect:/books/shelf";
            else{
                logger.info("exception remove book by title");
                throw new NonexistentBookException("errorMessageRemoveBookByTitle", "title does not exist");
            }
        }
    }

    @PostMapping("/remove/size")
    public String removeBookBySize(@RequestParam(name = "bookSizeToRemove") String bookSizeToRemove) throws NonexistentBookException{
        if(bookSizeToRemove.isEmpty()){
            throw new NonexistentBookException("errorMessageRemoveBookBySize", "size empty!");
        }else {
            if(bookService.removeBookBySize(Integer.valueOf(bookSizeToRemove)))
                return "redirect:/books/shelf";
            else{
                logger.info("exception remove book by size");
                throw new NonexistentBookException("errorMessageRemoveBookBySize", "size does not exist");
            }
        }
    }

    @PostMapping("/filter/author")
    public String filterBookByAuthor(@RequestParam(name = "bookAuthorToFilter") String bookAuthorToFilter, Model model) throws NonexistentBookException{
        if(bookAuthorToFilter.isEmpty())
            throw new NonexistentBookException("errorMessageFilterBookByAuthor", "author empty!");
        else {
            List<Book> books = bookService.getBooksByAuthor(bookAuthorToFilter);
            if(books.isEmpty())
                throw new NonexistentBookException("errorMessageFilterBookByAuthor", "author does not exist");
            else {
                model.addAttribute("book", new Book());
                model.addAttribute("bookList", books);
                model.addAttribute("fileList", fileService.getFilesName());
                return "book_shelf";
            }
        }
    }

    @PostMapping("/filter/title")
    public String filterBookByTitle(@RequestParam(name = "bookTitleToFilter") String bookTitleToFilter, Model model) throws NonexistentBookException{
        if(bookTitleToFilter.isEmpty())
            throw new NonexistentBookException("errorMessageFilterBookByTitle", "title empty!");
        else {
            List<Book> books = bookService.getBooksByTitle(bookTitleToFilter);
            if(books.isEmpty())
                throw new NonexistentBookException("errorMessageFilterBookByTitle", "title does not exist");
            else {
                model.addAttribute("book", new Book());
                model.addAttribute("bookList", books);
                model.addAttribute("fileList", fileService.getFilesName());
                return "book_shelf";
            }
        }
    }

    @PostMapping("/filter/size")
    public String filterBookBySize(@RequestParam(name = "bookSizeToFilter") String bookSizeToFilter, Model model) throws NonexistentBookException{
        if(bookSizeToFilter.isEmpty())
            throw new NonexistentBookException("errorMessageFilterBookBySize", "size empty!");
        else {
            List<Book> books = bookService.getBooksBySize(Integer.valueOf(bookSizeToFilter));
            if(books.isEmpty())
                throw new NonexistentBookException("errorMessageFilterBookBySize", "size does not exist");
            else {
                model.addAttribute("book", new Book());
                model.addAttribute("bookList", books);
                model.addAttribute("fileList", fileService.getFilesName());
                return "book_shelf";
            }
        }
    }

    @PostMapping("/filter/disable")
    public String filterBookDisable(Model model){
        logger.info("filter book is disable");
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks());
        model.addAttribute("fileList", fileService.getFilesName());
        return "redirect:/books/shelf";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException, NonexistentBookException {

        if(!file.isEmpty()) {
            fileService.uploadFile(file);
            return "redirect:/books/shelf";
        }
        else{
            logger.info("null file");
            throw new NonexistentBookException("errorMessageUploadFile", "file not selected");
        }
    }

    @PostMapping("/downloadFile")
    public Object downloadFile(@RequestParam("fileName") String fileName) throws IOException {
        return fileService.downloadFile(fileName);
    }

    @ExceptionHandler(NonexistentBookException.class)
    public String nonexistentBookException(Model model, NonexistentBookException exception){
        model.addAttribute("book", new Book());
        model.addAttribute(exception.getAttribute(), exception.getMessage());
        model.addAttribute("bookList", bookService.getAllBooks());
        model.addAttribute("fileList", fileService.getFilesName());
        return "book_shelf";
    }
}