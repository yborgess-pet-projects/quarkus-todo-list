package org.quarkus.demo.todolist;

import jakarta.ws.rs.core.Response;

public class ErrorResponse {
   private final Response.StatusType statusType;
   private final String path;
   private final String message;

    public ErrorResponse(Response.StatusType statusType, String path, String message) {
        this.statusType = statusType;
        this.path = path;
        this.message = message;
    }

    public String getError() {
        return statusType.getReasonPhrase();
    }

    public String getPath() {
        return path;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return statusType.getStatusCode();
    }
}
