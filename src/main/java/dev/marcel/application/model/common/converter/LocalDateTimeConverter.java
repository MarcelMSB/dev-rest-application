package dev.marcel.application.model.common.converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@SuppressWarnings("deprecation")
@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime objectValue) {
        if (objectValue == null) {
            return null;
        }

        return new Timestamp(objectValue.getYear() - 1900, objectValue.getMonthValue() - 1,
                objectValue.getDayOfMonth(), objectValue.getHour(), objectValue.getMinute(),
                objectValue.getSecond(), objectValue.getNano());
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp dataValue) {
        if (dataValue == null) {
            return null;
        }

        return LocalDateTime.of(dataValue.getYear() + 1900, dataValue.getMonth() + 1,
                dataValue.getDate(), dataValue.getHours(), dataValue.getMinutes(),
                dataValue.getSeconds(), dataValue.getNanos());
    }
}
