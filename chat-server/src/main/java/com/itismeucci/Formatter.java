package com.itismeucci;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Formatter {
    public static Sendable deserialize(String data) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Sendable obj = mapper.readValue(data, Sendable.class);
        return obj;
    }

    public static String serialize(Sendable data) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(data) + "\0";
    }
}
