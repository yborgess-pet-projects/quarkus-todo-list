package org.quarkus.demo.todolist;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(NotFoundException e) {
        ErrorResponse error = new ErrorResponse(e.getResponse().getStatusInfo(), uriInfo.getPath(), e.getMessage());

        return Response.status(e.getResponse().getStatusInfo())
                .entity(error)
                .build();
    }
}
