package com.webflux.reactivetodoapi;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    public Flux<ResponseDto> findAll() {
       return todoRepository.findAll()
               .map(todo -> new ResponseDto(
                       todo.getId(),
                       todo.getTitle(),
                       todo.isCompleted()
               ));
    }

    public Mono<ResponseDto> findById(Long id) {
        return todoRepository.findById(id)
                .map(todo -> new ResponseDto(
                        todo.getId(),
                        todo.getTitle(),
                        todo.isCompleted()
                ))
                .switchIfEmpty(Mono.error(new TodoNotFoundException(id)));
    }

    public Mono<ResponseDto> save(RequestDto request) {
        Todo todo = new Todo();
        todo.setTitle(request.title());
        todo.setCompleted(false);
       return todoRepository.save(todo)
               .map(t -> new ResponseDto(
                       t.getId(),
                       t.getTitle(),
                       t.isCompleted()
               ));
    }

    public Mono<Void> deleteById(Long id) {
        return todoRepository.findById(id)
                .switchIfEmpty(Mono.error(new TodoNotFoundException(id)))
                .flatMap(todo -> todoRepository.deleteById(id));
    }

    public Mono<ResponseDto> updateTodoStatus(Boolean completed, Long id) {
               return  todoRepository.findById(id)
                       .switchIfEmpty(Mono.error(new TodoNotFoundException(id)))
                       .map(t -> {
                        t.setCompleted(completed);
                        return t;
                         })
                       .flatMap(todoRepository::save)
                       .map(todo -> new ResponseDto(
                               todo.getId(),
                               todo.getTitle(),
                               todo.isCompleted()
                       ));
    }
}
