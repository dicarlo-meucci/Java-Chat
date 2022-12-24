package com.itismeucci;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Formatter {
    public static Sendable deserialize(String data)
    {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Sendable obj = mapper.readValue(data, Sendable.class);
            return obj;
        } catch (Exception e)
        {
            if (data.isEmpty())
            return null;
            
            System.out.println("Deserialization error: " + e.getMessage());
        }

        return null;
    }

    public static String serialize(Sendable data) throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(data) + '\0';
    }

    public static void deleteConsoleLine()
    {
        System.out.print("\033[2K");
    }
}
