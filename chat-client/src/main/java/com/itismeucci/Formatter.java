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
            System.out.println("Deserialization error: " + e.getMessage());
        }

        return null;
        // switch (obj.getType())
        // {
        //     case Constants.TYPE_MESSAGE:
        //     break;
        //     case Constants.TYPE_NOTIFICATION:
        //     break;
        //     case Constants.TYPE_RESPONSE:
        //     break;
        //     case Constants.TYPE_COMMAND:
        //     break;
        // }
    }

    public static String serialize(Sendable data) throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(data) + '\0';
    }
}
