package com.thoughtworks.springbootemployee.advice;

public class ErrorResponse {
    private String message;
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public ErrorResponse(String message, String status) {
        this.message = message;
        this.status = status;
    }
}
