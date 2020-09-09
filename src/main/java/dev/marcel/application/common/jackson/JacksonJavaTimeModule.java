package dev.marcel.application.common.jackson;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import com.fasterxml.jackson.databind.*;

public class JacksonJavaTimeModule extends SimpleModule {

    public JacksonJavaTimeModule() {
        super("JacksonJavaTimeModule", Version.unknownVersion());

        this.addSerializer(Year.class, new YearSerializer(Year.class));
        this.addSerializer(LocalDate.class, new LocalDateSerializer(LocalDate.class));
        this.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());

        this.addDeserializer(Year.class, new YearDeserializer(Year.class));
        this.addDeserializer(LocalDate.class, new LocalDateDeserializer(LocalDate.class));
        this.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
    }

    public static final class YearSerializer extends StdSerializer<Year> {

        protected YearSerializer(Class<Year> t) {
            super(t);
        }

        @Override
        public void serialize(Year value, JsonGenerator jgen, SerializerProvider provider)
                throws IOException {
            jgen.writeNumber(value.getValue());
        }
    }

    public static final class LocalDateSerializer extends StdSerializer<LocalDate> {

        protected LocalDateSerializer(Class<LocalDate> t) {
            super(t);
        }

        @Override
        public void serialize(LocalDate value, JsonGenerator generator, SerializerProvider provider) throws IOException {
            generator.writeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
    }

    public static final class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

        public static final DateTimeFormatter LOCAL_DATE_TIME = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(ISO_LOCAL_DATE)
                .appendLiteral(' ')
                .append(ISO_LOCAL_TIME)
                .toFormatter();

        @Override
        public void serialize(LocalDateTime value, JsonGenerator generator, SerializerProvider provider) throws IOException {
            if (value != null) {
                generator.writeString(LOCAL_DATE_TIME.format(value));
            }
        }
    }

    public static final class YearDeserializer extends StdScalarDeserializer<Year> {

        protected YearDeserializer(Class<Year> t) {
            super(t);
        }

        @Override
        public Year deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            switch (jp.getCurrentToken()) {
                case VALUE_STRING:
                    return Year.of(Integer.valueOf(jp.getText()));
                case VALUE_NUMBER_INT:
                    return Year.of(jp.getIntValue());
                default:
                    break;
            }
            ctxt.wrongTokenException(jp, JsonToken.START_ARRAY, "expected JSON Number");
            return null;
        }
    }

    public static final class LocalDateDeserializer extends StdScalarDeserializer<LocalDate> {

        protected LocalDateDeserializer(Class<LocalDate> t) {
            super(t);
        }

        @Override
        public LocalDate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            return LocalDate.parse(parser.readValueAs(String.class));
        }
    }

    public static final class LocalDateTimeDeserializer extends com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer {

        public static final DateTimeFormatter LOCAL_DATE_TIME = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(ISO_LOCAL_DATE)
                .appendLiteral(' ')
                .append(ISO_LOCAL_TIME)
                .toFormatter();

        public LocalDateTimeDeserializer() {
            super(LOCAL_DATE_TIME);
        }
    }
}
