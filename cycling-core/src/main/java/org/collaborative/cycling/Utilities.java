package org.collaborative.cycling;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.collaborative.cycling.models.Coordinates;

import java.io.IOException;
import java.util.List;


public class Utilities {

    private static ObjectMapper mapper = new ObjectMapper();
    private static ObjectMapper jsonMapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setVisibilityChecker(mapper.getSerializationConfig()
                .getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
    }

    public static String serialize(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static <T> T deserialize(String body, Class<T> type) {
        try {
            return jsonMapper.readValue(body, type);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
