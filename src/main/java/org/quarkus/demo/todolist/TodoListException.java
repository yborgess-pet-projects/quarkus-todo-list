package org.quarkus.demo.todolist;

import jakarta.ws.rs.core.Response;

public class TodoListException extends RuntimeException {
    private final Response.StatusType statusType;

    public TodoListException(String message, Response.StatusType statusType) {
        super(message);
        this.statusType = statusType;
    }

    public Response.StatusType getStatusType() {
        return statusType;
    }
}
