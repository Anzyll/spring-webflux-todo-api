package com.webflux.reactivetodoapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table("todo")
@AllArgsConstructor
public class Todo {
    @Id
    private Long id;
    private String title;
    private boolean completed;
}
