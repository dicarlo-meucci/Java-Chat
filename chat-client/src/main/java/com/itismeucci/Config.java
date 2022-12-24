package com.itismeucci;

public class Config
{
    private static String address;
    private static int port;

    public Config()
    {
        
    }

    public Config(String prefix, String address, int port) {
        Config.address = address;
        Config.port = port;
    }

    public String getAddress() {
        return Config.address;
    }

    public void setAddress(String address) {
        Config.address = address;
    }

    public int getPort() {
        return Config.port;
    }

    public void setPort(int port) {
        Config.port = port;
    }
}