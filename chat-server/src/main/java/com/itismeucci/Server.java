package com.itismeucci;

import java.util.ArrayList;
import java.net.*;

public class Server {
    private ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
    private ServerSocket server;
    private int port;

    public Server(int port)
    {
        this.port = port;
    }

    public void listen()
    {
        try {
            server = new ServerSocket(port);
            while (true)
            {
                Socket client = server.accept();
                ClientHandler handler = new ClientHandler(client);
                this.clients.add(handler);
                handler.start();
            }
        } catch (Exception e)
        {
            System.out.println("Errore durante l'inizializzazione del server: " + e.getMessage());
            System.exit(1);
        }
    }

    public void emit(String nome)
    {

    }

    public void broadcast()
    {

    }
}
