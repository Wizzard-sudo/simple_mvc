package org.example.app.repository;

import org.apache.log4j.Logger;
import org.example.app.repository.mappers.BookMapper;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository<Book> {

    private final Logger logger = Logger.getLogger(BookRepositoryImpl.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final BookMapper bookMapper;

    @Autowired
    public BookRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate, BookMapper bookMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookMapper = bookMapper;
    }

    @Override
    public List<Book> retreiveAll() {
        List<Book> books = jdbcTemplate.query("SELECT * FROM books", bookMapper);
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
        if (deletedCount != 0) {
            logger.info("remove book completed");
            return true;
        } else {
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
        List<Book> books = jdbcTemplate.query("SELECT * FROM books WHERE author = :author", parameterSource, bookMapper);
        return new ArrayList<>(books);
    }

    @Override
    public List<Book> filterItemByTitle(String bookAuthorToTitle) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("title", bookAuthorToTitle);
        List<Book> books = jdbcTemplate.query("SELECT * FROM books WHERE title = :title", parameterSource, bookMapper);
        return new ArrayList<>(books);
    }

    @Override
    public List<Book> filterItemBySize(Integer bookAuthorToSize) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("size", bookAuthorToSize);
        List<Book> books = jdbcTemplate.query("SELECT * FROM books WHERE size = :size", parameterSource, bookMapper);
        return new ArrayList<>(books);
    }
}