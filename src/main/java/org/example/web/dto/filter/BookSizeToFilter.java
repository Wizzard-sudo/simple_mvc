package org.example.web.dto.filter;

import javax.validation.constraints.NotNull;

public class BookSizeToFilter {

    @NotNull
    private Integer size;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
