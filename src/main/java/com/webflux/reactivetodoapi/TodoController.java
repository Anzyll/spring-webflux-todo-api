package com.webflux.reactivetodoapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<Todo> getAll(){
        return todoService.findAll();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Todo> getAll(@PathVariable Long id){
        return todoService.findById(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Todo> addTodo(@RequestBody Todo todo){
        return todoService.save(todo);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteTodo(@PathVariable Long id){
        return todoService.deleteById(id);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Todo> updateTodo( Boolean completed,@PathVariable Long id){
        return todoService.updateTodoStatus(completed,id);
    }

}
