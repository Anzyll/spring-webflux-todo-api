package com.webflux.reactivetodoapi;

public class TodoNotFoundException extends RuntimeException{
    public TodoNotFoundException(Long id){
        super("todo not found with id :"+id);
    }
}
