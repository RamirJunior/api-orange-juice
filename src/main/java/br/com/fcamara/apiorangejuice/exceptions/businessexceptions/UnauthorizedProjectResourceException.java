package br.com.fcamara.apiorangejuice.exceptions.businessexceptions;

public class UnauthorizedProjectResourceException extends RuntimeException{
    public UnauthorizedProjectResourceException(String message){
        super(message);
    }
}
