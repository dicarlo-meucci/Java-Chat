package com.itismeucci;

import java.util.ArrayList;

import java.net.*;

public class Server {
    private static ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
    private static ServerSocket server;
    private static int port;

    public static void configure(int port)
    {
        Server.port = port;
    }

    public static void listen()
    {
        try {
            Server.server = new ServerSocket(port);
            while (true)
            {
                Socket client = Server.server.accept();
                ClientHandler handler = new ClientHandler(client);
                Server.clients.add(handler);
                handler.start();
            }
        } catch (Exception e)
        {
            System.out.println("Errore durante l'inizializzazione del server: " + e.getMessage());
            System.exit(1);
        }
    }
    
    public static Sendable authenticate(Sendable obj, ClientHandler self)
    {
        Sendable response = new Sendable();
        for (ClientHandler client : clients)
        {
            if (client.getName().equals(obj.getUser()))
            {
                response.setStatus(Constants.STATUS_BAD_PARAMETERS);
                response.setResponse(Constants.RESPONSE_NAME_ALREADY_IN_USE);
            }
        }
        self.setName(obj.getUser());
        response.setStatus(Constants.STATUS_VALID);
        response.setResponse(Constants.RESPONSE_VALID);

        return response;
    }

    public static Sendable sendToOne(Sendable obj)
    {
        Sendable response = new Sendable();
        for (ClientHandler client : clients)
        {
            if (client.getName().equals(obj.getTarget()))
            {
                client.writeToStream(obj);
                response.setStatus(Constants.STATUS_VALID);
                response.setResponse(Constants.RESPONSE_VALID);
            }
        }

        response.setStatus(Constants.STATUS_NOT_FOUND);
        response.setResponse(Constants.RESPONSE_NOT_FOUND);

        return response;
    }

    public static Sendable sendToEveryone(Sendable obj)
    {
        Sendable response = new Sendable();

        if (clients.size() < 1)
        {
            response.setStatus(Constants.STATUS_NOT_FOUND);
            response.setResponse(Constants.RESPONSE_NOT_FOUND);
            return response;
        }

        for (ClientHandler client : clients)
        {
            if (!client.getName().equals(obj.getUser()))
            {
                client.writeToStream(obj);
            }
        }
        response.setStatus(Constants.STATUS_VALID);
        response.setResponse(Constants.RESPONSE_VALID);

        return response;
    }

    public static void notifyEveryone(Sendable obj)
    {
        for (ClientHandler client : clients)
        {
            if (!client.getName().equals(obj.getUser()))
            {
                client.writeToStream(obj);
            }
        }
    }
}