package org.example.app.exceptions;

public class RemoveBookException extends Exception{

    private String attribute;

    private String message;

    public RemoveBookException(String attribute ,String message){
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
