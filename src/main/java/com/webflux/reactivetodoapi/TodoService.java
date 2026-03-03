package com.webflux.reactivetodoapi;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TodoService {
    Map<Long,Todo> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();
    public Flux<Todo> findAll() {
        store.put(1L,new Todo(1L,"hi",true));
       return Flux.fromIterable(store.values())
               .doOnNext(todo -> System.out.println("Thread :" + Thread.currentThread().getName()))
               .delayElements(Duration.ofMillis(200));
    }

    public Mono<Todo> findById(Long id) {
        return Mono.justOrEmpty(store.get(id));
    }

    public Mono<Todo> save(Todo todo) {
        if(todo.getId()==null){
            todo.setId(idGenerator.incrementAndGet());
        }
        store.put(todo.getId(), todo);
        return  Mono.just(todo);
    }

    public Mono<Void> deleteById(Long id) {
          return Mono.justOrEmpty(store.remove(id))
                  .then();
    }

    public Mono<Todo> updateTodoStatus(Boolean completed, Long id) {
       return Mono.justOrEmpty(store.get(id))
                       .map(todo -> {
                           todo.setCompleted(completed);
                           store.put(id,todo);
                           return todo;
                       });


    }
}
