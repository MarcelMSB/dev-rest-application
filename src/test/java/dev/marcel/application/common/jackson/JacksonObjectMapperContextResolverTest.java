package dev.marcel.application.common.jackson;

import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(CdiRunner.class)
public class JacksonObjectMapperContextResolverTest {

    private static final String DATA = "2000-01-01";
    private static final String DATA_HORA = "2000-01-01 21:00:00.123";
    private static final String FORMAT_STRING = "\"%s\"";

    @Inject
    private JacksonObjectMapperContextResolver resouver;

    @Test
    public void shouldSerializerEntity() throws JsonProcessingException {
        final LocalDate data = LocalDate.parse(DATA);
        final LocalDateTime dataHora = LocalDateTime.parse(DATA_HORA, JacksonJavaTimeModule.LocalDateTimeDeserializer.LOCAL_DATE_TIME);
        final ObjectMapper objectMapper = resouver.createObjectMapper();
        assertEquals(String.format(FORMAT_STRING, DATA), objectMapper.writeValueAsString(data));
        assertEquals(String.format(FORMAT_STRING, DATA_HORA), objectMapper.writeValueAsString(dataHora));
    }
}
