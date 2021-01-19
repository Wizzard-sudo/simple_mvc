package org.example.web.dto.filter;

import javax.validation.constraints.NotEmpty;

public class BookTitleToFilter {

    @NotEmpty
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
