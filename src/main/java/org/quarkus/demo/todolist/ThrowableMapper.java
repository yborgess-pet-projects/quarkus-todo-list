package org.quarkus.demo.todolist;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ThrowableMapper implements ExceptionMapper<Throwable> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(Throwable e) {
        ErrorResponse error = new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, uriInfo.getPath(), e.getMessage());

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(error)
                .build();

    }
}
