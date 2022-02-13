package com.egs.bankservice.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response<T> {
    private Date timestamp;
    private int status;
    private String error;
    private T message;
    private String path;

    public Response() {}

    public Response(String mode, String path) {
        if ("OK".equals(mode)) {
            this.timestamp = new Date();
            this.status = 200;
            this.error = "OK";
            this.path = path;
        }
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Response<T> setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public Response<T> setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getError() {
        return error;
    }

    public Response<T> setError(String error) {
        this.error = error;
        return this;
    }

    public String getPath() {
        return path;
    }

    public Response<T> setPath(String path) {
        this.path = path;
        return this;
    }

    public T getMessage() {
        return message;
    }

    public Response<T> setMessage(T message) {
        this.message = message;
        return this;
    }
}
