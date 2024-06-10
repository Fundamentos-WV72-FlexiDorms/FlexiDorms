package com.techartistry.accountservice.shared.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    /**
     * Constructor de la clase ResourceNotFoundException
     * @param resourceName Nombre del recurso que no se ha encontrado
     * @param fieldName Nombre del campo por el que se ha buscado
     * @param fieldValue Valor del campo por el que se ha buscado
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with field '%s': '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
