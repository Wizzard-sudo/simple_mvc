package org.example.app.repository;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();
    private ApplicationContext context;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> retreiveAll() {
        List<Book> books = jdbcTemplate.query("SELECT * FROM books", (ResultSet rs, int rowNum) ->{
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });
        return new ArrayList<>(books);
    }

    @Override
    public void store(Book book) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", book.getAuthor());
        parameterSource.addValue("title", book.getTitle());
        parameterSource.addValue("size", book.getSize());
        jdbcTemplate.update("INSERT INTO books(author, title, size) VALUES(:author, :title, :size)", parameterSource);
        logger.info("store new book: " + book);
    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        int deletedCount;
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", bookIdToRemove);
        deletedCount = jdbcTemplate.update("DELETE FROM books WHERE id = :id", parameterSource);
        if(deletedCount != 0) {
            logger.info("remove book completed");
            return true;
        }else{
            logger.info("remove book failed");
            return false;
        }
    }

    @Override
    public boolean removeItemByAuthor(String bookAuthorToRemove) {
        int deletedCount;
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", bookAuthorToRemove);
        deletedCount = jdbcTemplate.update("DELETE FROM books WHERE author = :author", parameterSource);
        if (deletedCount != 0) {
            logger.info("remove book completed");
            return true;
        } else {
            logger.info("remove book failed");
            return false;
        }
    }

    @Override
    public boolean removeItemByTitle(String bookTitleToRemove) {
        int deletedCount;
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("title", bookTitleToRemove);
        deletedCount = jdbcTemplate.update("DELETE FROM books WHERE title = :title", parameterSource);
        if (deletedCount != 0) {
            logger.info("remove book completed");
            return true;
        } else {
            logger.info("remove book failed");
            return false;
        }
    }

    @Override
    public boolean removeItemBySize(Integer bookSizeToRemove) {
        int deletedCount;
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("size", bookSizeToRemove);
        deletedCount = jdbcTemplate.update("DELETE FROM books WHERE size = :size", parameterSource);
        if (deletedCount != 0) {
            logger.info("remove book completed");
            return true;
        } else {
            logger.info("remove book failed");
            return false;
        }
    }

    @Override
    public List<Book> filterItemByAuthor(String bookAuthorToFilter) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", bookAuthorToFilter);
        List<Book> books = jdbcTemplate.query("SELECT * FROM books WHERE author = :author", parameterSource, (ResultSet rs, int rowNum) ->{
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });
            return new ArrayList<>(books);
    }

    @Override
    public List<Book> filterItemByTitle(String bookAuthorToTitle) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("title", bookAuthorToTitle);
        List<Book> books = jdbcTemplate.query("SELECT * FROM books WHERE title = :title", parameterSource, (ResultSet rs, int rowNum) ->{
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });
        return new ArrayList<>(books);
    }

    @Override
    public List<Book> filterItemBySize(Integer bookAuthorToSize) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("size", bookAuthorToSize);
        List<Book> books = jdbcTemplate.query("SELECT * FROM books WHERE size = :size", parameterSource, (ResultSet rs, int rowNum) ->{
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });
        return new ArrayList<>(books);
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
