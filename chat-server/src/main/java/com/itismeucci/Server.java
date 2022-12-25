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
            System.out.println("Errore during server initialization: " + e.getMessage());
            System.exit(1);
        }
    }
    
    public static Sendable authenticate(Sendable obj, ClientHandler client)
    {
        Sendable response = new Sendable();
        response.setType(Constants.TYPE_RESPONSE);

        if (obj.getUser().contains(" ") || obj.getUser().length() < 3 || obj.getUser().length() > 16)
        {
            response.setStatus(Constants.STATUS_BAD_PARAMETERS);
            response.setResponse(Constants.RESPONSE_NAME_NOT_VALID);
            return response;
        }

        for (ClientHandler c : clients)
        {
            if (c.getName().equals(obj.getUser()))
            {
                response.setStatus(Constants.STATUS_BAD_PARAMETERS);
                response.setResponse(Constants.RESPONSE_NAME_ALREADY_IN_USE);
                return response;
            }
        }
        client.setName(obj.getUser());
        response.setStatus(Constants.STATUS_VALID);
        response.setResponse(Constants.RESPONSE_VALID);
        System.out.println(client.getName() + " connected");
        return response;
    }

    public static Sendable sendToOne(Sendable obj)
    {
        Sendable response = new Sendable();
        response.setType(Constants.TYPE_RESPONSE);
        
        for (ClientHandler client : clients)
        {
            System.out.println(client.getName());
            if (client.getName().equals(obj.getTarget()))
            {
                client.writeToStream(obj);
                response.setStatus(Constants.STATUS_VALID);
                response.setResponse(Constants.RESPONSE_VALID);
                return response;
            }
        }

        response.setStatus(Constants.STATUS_NOT_FOUND);
        response.setResponse(Constants.RESPONSE_NOT_FOUND);

        return response;
    }

    public static Sendable sendToEveryone(Sendable obj)
    {
        Sendable response = new Sendable();
        response.setType(Constants.TYPE_RESPONSE);

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

    public static Sendable command(Sendable obj)
    {
        Sendable response = new Sendable();
        response.setType(Constants.TYPE_COMMAND_RESPONSE);

        if (obj.getContent().equals("list"))
        {
            ArrayList<String> participants = new ArrayList<>();
            for (ClientHandler c : clients)
            {
                participants.add(c.getName());
            }
            response.setParticipants(participants);
            response.setStatus(Constants.STATUS_VALID);
            return response;
        }

        response.setStatus(Constants.STATUS_NOT_FOUND);
        response.setResponse("The command " + obj.getContent() + " does not exist");

        return response;
    }

    public static void disconnect(Sendable obj, ClientHandler client)
    {
        try {
            client.getClient().close();
        } 
        catch (Exception e) {}
        ArrayList<ClientHandler> clientsCopy = new ArrayList<>(clients);
        for (ClientHandler c : clientsCopy) {
            if (obj.getUser().equals(c.getName())) {
                clients.remove(c);
                System.out.println(c.getName() + " disconnected");
            }
        }
    }
}