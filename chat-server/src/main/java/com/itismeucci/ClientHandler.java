package com.itismeucci;
import java.net.*;

public class ClientHandler extends Thread {
    private Socket client;
    public ClientHandler(Socket client)
    {
        this.client = client;
        System.out.println("Client connesso");
    }

    @Override
    public void run()
    {
        
    }
}
