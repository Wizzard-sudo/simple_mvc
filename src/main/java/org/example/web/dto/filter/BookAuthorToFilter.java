package org.example.web.dto.filter;

import javax.validation.constraints.NotEmpty;

public class BookAuthorToFilter {

    @NotEmpty
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
