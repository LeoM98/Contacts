package com.contact.app.domain.enums.converter;

import com.contact.app.domain.enums.DocumentType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class DocumentTypeConverter implements AttributeConverter<DocumentType, String> {

    @Override
    public String convertToDatabaseColumn(DocumentType dt) {

        return switch (dt) {
            case CÉDULA -> "CC";
            case TARJETA_IDENTIDAD -> "TI";
        };
    }

    @Override
    public DocumentType convertToEntityAttribute(String dt) {
        return switch (dt) {
            case "CC" -> DocumentType.CÉDULA;
            case "TI" -> DocumentType.TARJETA_IDENTIDAD;
            default -> throw new IllegalStateException("Unexpected value: " + dt);
        };
    }
}
