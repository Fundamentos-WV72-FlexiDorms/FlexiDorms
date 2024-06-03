package com.techartistry.roomsservice.rooms.infrastructure.mappers;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;

/**
 * Clase que convierte una lista de strings a un string separado por comas y viceversa
 */
@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        return String.join(DELIMITER, attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return Arrays.stream(dbData.split(DELIMITER)).toList();
    }
}
