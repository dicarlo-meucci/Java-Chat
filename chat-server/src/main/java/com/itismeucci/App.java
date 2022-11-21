package com.itismeucci;

public class App 
{
    public static void main( String[] args )
    {
        int port = 8080;
        System.out.println(args[0]);
        if (args[0].equals("-p"))
        {
            port = Integer.parseInt(args[1]);
        }
        System.out.println("Server opened on port " + port);
    }
}
