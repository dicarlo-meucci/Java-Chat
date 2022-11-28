package com.itismeucci;

public class Config
{
    private static String prefix;
    private static String address;
    private static int port;

    public Config()
    {
        
    }

    public Config(String prefix, String address, int port) {
        Config.prefix = prefix;
        Config.address = address;
        Config.port = port;
    }

    public String getPrefix() {
        return Config.prefix;
    }

    public void setPrefix(String prefix) {
        Config.prefix = prefix;
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