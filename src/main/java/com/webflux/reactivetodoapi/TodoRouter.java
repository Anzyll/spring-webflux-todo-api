package com.webflux.reactivetodoapi;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class TodoRouter {
    @Bean
    public RouterFunction<ServerResponse> route(TodoHandler handler){
        return RouterFunctions.route()
                .GET("/api/todos",handler::getAllTodos)
                .GET("/api/todos/{id}",handler::getTodo)
                .POST("/api/todos",handler::addTodo)
                .DELETE("/api/todos/{id}",handler::deleteTodo)
                .PATCH("/api/todos/{id}",handler::updateTodo)
                .build();
    }
}
