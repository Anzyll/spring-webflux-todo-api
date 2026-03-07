package com.webflux.reactivetodoapi;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class TodoServiceBackpressureTest {
    @Test
    void backpressureTest(){
       Flux<ResponseDto> todos = Flux.just(
                new ResponseDto(1L,"go gym",false),
                new ResponseDto(2L,"drink water",false),
                new ResponseDto(3L,"do leetcode",false)
        );
        StepVerifier.create(todos,1)
                .expectNextCount(1)
                .thenRequest(2)
                .expectNextCount(2)
                .verifyComplete();
    }
}
