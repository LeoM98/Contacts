package com.contact.app.exceptions;

public enum ExceptionsCode {

    BAD_PARAMS("L-001"),
    CLOSED_DAYS("L-002"),
    NOT_FOUND("L-003");

    private String code;

    ExceptionsCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
