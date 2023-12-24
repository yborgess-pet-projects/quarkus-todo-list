package org.quarkus.demo.todolist;

import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.runtime.Startup;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;
import org.quarkus.demo.todolist.todos.Todo;
import org.quarkus.demo.todolist.todos.TodoRepository;

@IfBuildProfile(anyOf = {"dev", "test"})
public class DataInitializer {

    @Inject
    TodoRepository repository;

    private static final Logger LOG = Logger.getLogger(DataInitializer.class);

    @Startup
    @Transactional
    public void init() {
        LOG.info("Data Base initializer");
        repository.deleteAll();
        repository.persist(new Todo("Buy tickets for the concert", false));
        repository.persist(new Todo("Learn how to make a cheesecake", true));
        repository.persist(new Todo("Replace the bicycle light batteries", false));
    }
}
