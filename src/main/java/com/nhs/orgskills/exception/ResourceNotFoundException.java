package com.nhs.orgskills.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String entityName, String fieldName,  Long fieldValue) {
        super(String.format("%s not found with %s : %s", entityName, fieldName, fieldValue));
    }
}
