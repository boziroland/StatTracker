package org.github.boziroland.exceptions;

public class LoginException extends Exception{

    public LoginException(){
        super();
    }

    public LoginException(String message){
        super(message);
    }
}
