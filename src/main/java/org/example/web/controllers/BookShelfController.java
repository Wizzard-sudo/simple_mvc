package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.example.web.dto.BookIdToRemove;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.naming.Binding;
import javax.validation.Valid;

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
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("book", book);
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
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
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        }else {
            bookService.removeBookById(bookIdToRemove.getId());
            return "redirect:/books/shelf";
        }
     }

    @PostMapping("/remove/author")
    public String removeBookByAuthor(@RequestParam(value = "bookAuthorToRemove") String bookAuthorToRemove){
        if(bookService.removeBookByAuthor(bookAuthorToRemove)){
            return "redirect:/books/shelf";
        }else{
            logger.info("entry does not exist");
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/remove/title")
    public String removeBookByTitle(@RequestParam(value = "bookTitleToRemove") String bookTitleToRemove){
        if(bookService.removeBookByTitle(bookTitleToRemove)){
            return "redirect:/books/shelf";
        }else{
            logger.info("entry does not exist");
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/remove/size")
    public String removeBookBySize(@RequestParam(value = "bookSizeToRemove") Integer bookSizeToRemove){
        if(bookService.removeBookBySize(bookSizeToRemove)){
            return "redirect:/books/shelf";
        }else{
            logger.info("entry does not exist");
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/filter/author")
    public String filterBookByAuthor(@RequestParam(value = "bookAuthorToFilter") String bookAuthorToFilter, Model model){
        logger.info("filter book by author: " + bookAuthorToFilter);
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getBooksByAuthor(bookAuthorToFilter));
        return "book_shelf";
    }

    @PostMapping("/filter/title")
    public String filterBookByTitle(@RequestParam(value = "bookTitleToFilter") String bookTitleToFilter, Model model){
        logger.info("filter book by title: " + bookTitleToFilter);
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getBooksByTitle(bookTitleToFilter));
        return "book_shelf";
    }

    @PostMapping("/filter/size")
    public String filterBookBySize(@RequestParam(value = "bookSizeToFilter") int bookSizeToFilter, Model model){
        logger.info("filter book by size: " + bookSizeToFilter);
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getBooksBySize(bookSizeToFilter));
        return "book_shelf";
    }

    @PostMapping("/filter/disable")
    public String filterBookDisable(Model model){
        logger.info("filter book is disable");
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "redirect:/books/shelf";
    }

}
