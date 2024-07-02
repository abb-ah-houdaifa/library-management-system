package com.library.library_management_system.exception;

public class OperationNotAllowedException extends RuntimeException{
    public OperationNotAllowedException(String msg){
        super(msg);
    }
}
