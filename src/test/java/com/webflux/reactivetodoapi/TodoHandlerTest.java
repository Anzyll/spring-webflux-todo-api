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


@WebFluxTest
@Import({TodoRouter.class,TodoHandler.class})
public class TodoHandlerTest {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private TodoService todoService;
    @Test
    void testGetTodos(){
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
}
