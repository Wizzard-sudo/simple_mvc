package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.exceptions.UploadNullFileException;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.example.web.dto.filter.BookAuthorToFilter;
import org.example.web.dto.filter.BookSizeToFilter;
import org.example.web.dto.filter.BookTitleToFilter;
import org.example.web.dto.remove.BookAuthorToRemove;
import org.example.web.dto.remove.BookIdToRemove;
import org.example.web.dto.remove.BookSizeToRemove;
import org.example.web.dto.remove.BookTitleToRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Controller
@RequestMapping(value = "books")
@Scope("singleton")
public class BookShelfController {

    private Logger logger = Logger.getLogger(BookShelfController.class);
    private BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model){
        logger.info(this.toString());
        model.addAttribute("book", new Book());
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
        model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
        model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
        model.addAttribute("bookAuthorToFilter", new BookAuthorToFilter());
        model.addAttribute("bookTitleToFilter", new BookTitleToFilter());
        model.addAttribute("bookSizeToFilter", new BookSizeToFilter());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("book", book);
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
            model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
            model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
            model.addAttribute("bookAuthorToFilter", new BookAuthorToFilter());
            model.addAttribute("bookTitleToFilter", new BookTitleToFilter());
            model.addAttribute("bookSizeToFilter", new BookSizeToFilter());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        }else{
            if(book.getTitle() == "" && book.getAuthor() == "" && book.getSize() == null)
                logger.info("attempt to create an EMPTY book: creation canceled");
            else {
                bookService.saveBook(book);
                logger.info("current repository size: " + bookService.getAllBooks().size());
            }
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/remove/id")
    public String removeBookById(@Valid BookIdToRemove bookIdToRemove, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("book", new Book());
            model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
            model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
            model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
            model.addAttribute("bookAuthorToFilter", new BookAuthorToFilter());
            model.addAttribute("bookTitleToFilter", new BookTitleToFilter());
            model.addAttribute("bookSizeToFilter", new BookSizeToFilter());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        }else {
            bookService.removeBookById(bookIdToRemove.getId());
            return "redirect:/books/shelf";
        }
     }

    @PostMapping("/remove/author")
    public String removeBookByAuthor(@Valid BookAuthorToRemove bookAuthorToRemove, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
            model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
            model.addAttribute("bookAuthorToFilter", new BookAuthorToFilter());
            model.addAttribute("bookTitleToFilter", new BookTitleToFilter());
            model.addAttribute("bookSizeToFilter", new BookSizeToFilter());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        }else {
            bookService.removeBookByAuthor(bookAuthorToRemove.getAuthor());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/remove/title")
    public String removeBookByTitle(@Valid BookTitleToRemove bookTitleToRemove, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
            model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
            model.addAttribute("bookAuthorToFilter", new BookAuthorToFilter());
            model.addAttribute("bookTitleToFilter", new BookTitleToFilter());
            model.addAttribute("bookSizeToFilter", new BookSizeToFilter());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        }else {
            bookService.removeBookByTitle(bookTitleToRemove.getTitle());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/remove/size")
    public String removeBookBySize(@Valid BookSizeToRemove bookSizeToRemove, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
            model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
            model.addAttribute("bookAuthorToFilter", new BookAuthorToFilter());
            model.addAttribute("bookTitleToFilter", new BookTitleToFilter());
            model.addAttribute("bookSizeToFilter", new BookSizeToFilter());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        }else {
            bookService.removeBookBySize(bookSizeToRemove.getSize());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/filter/author")
    public String filterBookByAuthor(@Valid BookAuthorToFilter bookAuthorToFilter, BindingResult bindingResult, Model model){
        logger.info("filter book by author: " + bookAuthorToFilter);
        model.addAttribute("book", new Book());
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
        model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
        model.addAttribute("bookTitleToFilter", new BookTitleToFilter());
        model.addAttribute("bookSizeToFilter", new BookSizeToFilter());
        model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
        if(bindingResult.hasErrors()){
            model.addAttribute("bookList", bookService.getAllBooks());
        }else {
            model.addAttribute("bookList", bookService.getBooksByAuthor(bookAuthorToFilter.getAuthor()));
        }
        return "book_shelf";
    }

    @PostMapping("/filter/title")
    public String filterBookByTitle(@Valid BookTitleToFilter bookTitleToFilter, BindingResult bindingResult, Model model){
        model.addAttribute("book", new Book());
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
        model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
        model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
        model.addAttribute("bookAuthorToFilter", new BookAuthorToFilter());
        model.addAttribute("bookSizeToFilter", new BookSizeToFilter());
        if(bindingResult.hasErrors()){
            model.addAttribute("bookList", bookService.getAllBooks());
        }else {
            model.addAttribute("bookList", bookService.getBooksByTitle(bookTitleToFilter.getTitle()));
        }
        return "book_shelf";
    }

    @PostMapping("/filter/size")
    public String filterBookBySize(@Valid BookSizeToFilter bookSizeToFilter, BindingResult bindingResult, Model model){
        model.addAttribute("book", new Book());
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
        model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
        model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
        model.addAttribute("bookAuthorToFilter", new BookAuthorToFilter());
        model.addAttribute("bookTitleToFilter", new BookTitleToFilter());
        if(bindingResult.hasErrors()){
            model.addAttribute("bookList", bookService.getAllBooks());
        }else {
            model.addAttribute("bookList", bookService.getBooksBySize(bookSizeToFilter.getSize()));
        }
        return "book_shelf";
    }

    @PostMapping("/filter/disable")
    public String filterBookDisable(Model model){
        logger.info("filter book is disable");
        model.addAttribute("book", new Book());
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
        model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
        model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
        model.addAttribute("bookAuthorToFilter", new BookAuthorToFilter());
        model.addAttribute("bookTitleToFilter", new BookTitleToFilter());
        model.addAttribute("bookSizeToFilter", new BookSizeToFilter());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "redirect:/books/shelf";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws Exception, UploadNullFileException {

        if(!file.isEmpty()) {
            logger.info("start upload :");
            String name = file.getOriginalFilename();
            byte[] bytes = file.getBytes();

            //create dir
            String rootPath = System.getProperty("catalina.home");
            File dir = new File(rootPath + File.separator + "external_uploads");
            if (!dir.exists())
                dir.mkdirs();

            //create file
            File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();

            logger.info("new file saved at:" + serverFile.getAbsolutePath());

            return "redirect:/books/shelf";
        }
        else{
            logger.info("null file");
            throw new UploadNullFileException("No file selected");
        }
    }

    @ExceptionHandler(UploadNullFileException.class)
    public String fileErrorHandler(Model model, UploadNullFileException exception){
        logger.info("No file selected");
        model.addAttribute("book", new Book());
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
        model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
        model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
        model.addAttribute("bookAuthorToFilter", new BookAuthorToFilter());
        model.addAttribute("bookTitleToFilter", new BookTitleToFilter());
        model.addAttribute("bookSizeToFilter", new BookSizeToFilter());
        model.addAttribute("errorMessageUploadFile", exception.getMessage());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }
}
