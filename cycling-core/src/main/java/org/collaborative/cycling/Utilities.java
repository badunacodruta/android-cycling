package org.collaborative.cycling;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;


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

//    public static boolean isNullOrEmpty(String value) {
//        return value == null || value.length() == 0;
//    }

//    public static void createDirectoryIfDoesNotExist(String path) {
//        File file = new File(path);
//        if (!file.exists()) {
//            if (!file.mkdirs()) {
//                throw new RuntimeException("Could not create directory");
//            }
//        } else if (!file.isDirectory()) {
//            throw new RuntimeException("Could not create directory because path already exists and it is not a directory");
//        }
//    }

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
}
