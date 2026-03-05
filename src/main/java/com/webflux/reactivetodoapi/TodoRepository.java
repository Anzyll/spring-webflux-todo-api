package com.webflux.reactivetodoapi;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface TodoRepository extends ReactiveCrudRepository<Todo,Long> {
    @Query("SELECT * FROM todo LIMIT :size OFFSET :offset")
    Flux<Todo> findAllPaged(int size, int offset);
}
