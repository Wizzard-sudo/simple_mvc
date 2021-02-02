package org.example.app.exceptions;

public class NonexistentBookException extends Exception{

    private final String attribute;

    private final String message;

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
