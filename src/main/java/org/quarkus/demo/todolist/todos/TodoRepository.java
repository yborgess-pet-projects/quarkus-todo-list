package org.quarkus.demo.todolist.todos;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class TodoRepository implements PanacheRepository<Todo> {

}
