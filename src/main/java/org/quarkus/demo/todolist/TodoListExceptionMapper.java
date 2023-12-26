package org.quarkus.demo.todolist;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class TodoListExceptionMapper implements ExceptionMapper<TodoListException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(TodoListException e) {
        ErrorResponse error = new ErrorResponse(e.getStatusType(), uriInfo.getPath(), e.getMessage());

        return Response.status(e.getStatusType())
                .entity(error)
                .build();
    }

}
