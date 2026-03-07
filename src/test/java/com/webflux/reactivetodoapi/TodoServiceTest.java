package com.webflux.reactivetodoapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {
    @Mock
    private TodoRepository todoRepository;
    @InjectMocks
    private TodoService todoService;
    @Test
    void shouldReturnTodos_whenFindAllCalled(){
        when(todoRepository.findAllPaged(10,0))
                .thenReturn(Flux.just(
                        new Todo(1L,"do homework",false),
                        new Todo(2L,"go gym",false)
                        )
                );
        StepVerifier.create(todoService.findAll(0,10))
                .expectNextMatches(todo->todo.title().equals("do homework"))
                .expectNextMatches(todo->todo.title().equals("go gym"))
                .verifyComplete();
    }
    @Test
    void shouldReturnCorrectTodoCount_whenFindAllCalled(){
        when(todoRepository.findAllPaged(10,0))
                .thenReturn(Flux.just(
                                new Todo(1L,"do homework",false),
                                new Todo(2L,"go gym",false)
                        )
                );
        StepVerifier.create(todoService.findAll(0,10))
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void shouldThrowException_whenTodoNotFound(){
        when(todoRepository.findById(1L))
                .thenReturn(Mono.empty());
        StepVerifier.create(todoService.findById(1L))
                .expectError(TodoNotFoundException.class)
                .verify();
    }

    @Test
    void shouldReturnTodo_whenValidRequest(){
        RequestDto todo = new RequestDto("do homework");
        when(todoRepository.save(any(Todo.class)))
                .thenReturn(Mono.just(new Todo(1L,"do homework",false)));
        StepVerifier.create(todoService.save(todo))
                .expectNextMatches(t->t.title().equals("do homework"))
                .verifyComplete();
    }

    @Test
    void shouldReturnTodo_whenFindByIdCalled(){
        when(todoRepository.findById(1L))
                .thenReturn(
                        Mono.just(new Todo(1L,"do homework",false))
                );
        StepVerifier.create(todoService.findById(1L))
                .expectNextMatches(todo->todo.title().equals("do homework"))
                .verifyComplete();
    }

    @Test
    void shouldDeleteTodo_whenIdExists(){
        when(todoRepository.findById(1L))
                .thenReturn(
                        Mono.just(new Todo(1L,"do homework",false))
                );
        when(todoRepository.deleteById(1L))
                .thenReturn(Mono.empty());
        StepVerifier.create(todoService.deleteById(1L))
                .verifyComplete();
    }


    @Test
    void shouldUpdateTodoStatus_whenValidRequest(){
        Todo todo = new Todo(1L,"do homework",false);
        when(todoRepository.findById(1L))
                .thenReturn(
                        Mono.just(todo)
                );
        when(todoRepository.save(any(Todo.class)))
                .thenReturn(Mono.just(new Todo(1L,"do homework",true)));
        StepVerifier.create(todoService.updateTodoStatus(true,1L))
                .expectNextMatches(ResponseDto::completed)
                .verifyComplete();
    }
}
