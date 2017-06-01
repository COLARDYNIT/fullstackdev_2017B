package com.mycompany.myapp.Exceptions;

/**
 * Created by stijnhaesendonck on 01/06/2017.
 */
public class InvalidMoodNameException extends Exception {
    public InvalidMoodNameException() {
    }

    public InvalidMoodNameException(String message) {
        super(message);
    }
}
