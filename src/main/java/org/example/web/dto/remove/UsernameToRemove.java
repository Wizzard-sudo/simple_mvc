package org.example.web.dto.remove;

import javax.validation.constraints.NotEmpty;

public class UsernameToRemove {

    @NotEmpty
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
