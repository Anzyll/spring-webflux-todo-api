package com.webflux.reactivetodoapi;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@WebFluxTest
@Import({TodoRouter.class,TodoHandler.class})
public class TodoHandlerTest {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private TodoService todoService;
    @Test
    void shouldReturnTodos_whenGetTodosEndpointCalled(){
        when(todoService.findAll(anyInt(),anyInt()))
                .thenReturn(Flux.just(
                        new ResponseDto(1L,"go gym",false),
                        new ResponseDto(2L,"drink water",false)
                ));
        webTestClient.get()
                .uri("/api/todos")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ResponseDto.class)
                .hasSize(2);
    }

    @Test
    void shouldReturnTodo_whenGetTodoByIdEndpointCalled(){
        when(todoService.findById(anyLong()))
                .thenReturn(Mono.just(
                        new ResponseDto(1L,"go gym",false)
                ));
        webTestClient.get()
                .uri("/api/todos/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ResponseDto.class);

    }

    @Test
    void shouldCreateTodo_whenPostTodosEndpointCalled(){
        RequestDto request = new RequestDto("go gym");
        when(todoService.save(any(RequestDto.class)))
                .thenReturn(Mono.just(
                        new ResponseDto(1L,"go gym",false)
                ));
        webTestClient.post()
                .uri("/api/todos")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ResponseDto.class);
    }

    @Test
    void shouldDeleteTodo_whenDeleteTodoEndpointCalled(){
        when(todoService.deleteById(anyLong()))
                .thenReturn(Mono.empty());
        webTestClient.delete()
                .uri("/api/todos/1")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void shouldUpdateTodo_whenUpdateTodoEndpointCalled(){
        ResponseDto response = new ResponseDto(1L,"go gym",true);
        when(todoService.updateTodoStatus(true,1L))
                .thenReturn(Mono.just(response));
        webTestClient.patch()
                .uri("/api/todos/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ResponseDto.class);
    }

}
