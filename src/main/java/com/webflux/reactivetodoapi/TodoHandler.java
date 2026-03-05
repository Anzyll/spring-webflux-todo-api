package com.webflux.reactivetodoapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TodoHandler {
    private final TodoService todoService;
    public Mono<ServerResponse> getAllTodos(ServerRequest request){
       return ServerResponse.status(HttpStatus.OK).body(
               todoService.findAll(),ResponseDto.class);
    }
    public Mono<ServerResponse> getTodo(ServerRequest request){
        Long id = Long.valueOf(request.pathVariable("id"));
        return todoService.findById(id)
                .flatMap(todo -> ServerResponse.ok().bodyValue(todo)
                        .switchIfEmpty(ServerResponse.notFound().build()));
    }
    public Mono<ServerResponse> addTodo(ServerRequest request) {
        return request.bodyToMono(RequestDto.class)
                .flatMap(todoService::save)
                .flatMap(todo ->
                        ServerResponse.status(HttpStatus.CREATED)
                                .bodyValue(todo)
                );
    }
    public Mono<ServerResponse> deleteTodo(ServerRequest request){
        Long id = Long.valueOf(request.pathVariable("id"));
        return todoService.deleteById(id)
                .then(ServerResponse.noContent().build());
    }

    public  Mono<ServerResponse> updateTodo(ServerRequest request){
        Long id = Long.valueOf(request.pathVariable("id"));
        return todoService.updateTodoStatus(true, id)
                .flatMap(todo -> ServerResponse.ok().bodyValue(todo));
    }
}
