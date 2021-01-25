package org.example.app.exceptions;

public class UploadNullFileException extends Exception{

    private String message;

    public UploadNullFileException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
