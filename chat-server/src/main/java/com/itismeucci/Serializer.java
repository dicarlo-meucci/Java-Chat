package com.itismeucci;

public class Serializer {
    public static Sendable demux(String data)
    {
        Sendable obj;
        switch (obj.getType())
        {
            case Constants.TYPE_MESSAGE:
            break;
            case Constants.TYPE_NOTIFICATION:
            break;
            case Constants.TYPE_RESPONSE:
            break;
        }
    }

    public static void mux(Sendable data)
    {

    }
}
