package org.example.app.exceptions;

public class NonexistentBookException extends Exception{

    private String attribute;

    private String message;

    public NonexistentBookException(String attribute , String message){
        this.attribute = attribute;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getAttribute() {
        return attribute;
    }
}
