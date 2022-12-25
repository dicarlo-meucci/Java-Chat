package com.itismeucci;

import java.util.*;
import java.io.*;
import java.net.*;

public class Client {
    private Socket client;
    private WriteThread write;
    private ReadThread read;
    private Scanner keyboard = new Scanner(System.in);
    private String username = "";
    public static boolean isAuthenticated = false;
    public Socket connect(String address, int port) throws IOException, InterruptedException {
        try {
            this.client = new Socket(address, port);
            this.read = new ReadThread(new DataInputStream(client.getInputStream()));
            this.write = new WriteThread(new DataOutputStream(client.getOutputStream()));
            read.start();
            write.start();
            authenticate();
        } catch (Exception e) {
            System.out.println("Connection error: " + e.getMessage());
            System.out.println("Retrying in 3 seconds...");
            Thread.sleep(3000);
        }

        return this.client;
    }

    public void authenticate() {
        if (!client.isConnected())
        {
            System.out.println("Authentication error: Client not connected to server");
            return;
        }
        System.out.print("Insert your name: ");
        String name = keyboard.nextLine();
        Sendable authentication = new Sendable();
        authentication.setType(Constants.TYPE_NOTIFICATION);
        authentication.setUser(name);
        authentication.setAction(Constants.ACTION_CONNECT);
        this.write.writeToStream(authentication);
        Sendable response = this.read.readFromStream();
        while (response == null || !response.getType().equals(Constants.TYPE_RESPONSE))
        response = this.read.readFromStream();

        if (response.getType().equals(Constants.TYPE_RESPONSE))
        {
            if (response.getStatus() == Constants.STATUS_VALID)
            {
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    public void run() {
                        disconnect();
                        Runtime.getRuntime().halt(0);
                    }
                });
                Client.isAuthenticated = true;
                this.username = name;
                System.out.println("You've successfully connected to the chatroom!");
                System.out.println("@<name> to send a Direct Message");
                System.out.println("/<command> to use commands");
                System.out.println();
                while (Client.isAuthenticated)
                {
                    String input = keyboard.nextLine();
                    if (input.isEmpty())
                    continue;

                    if (input.startsWith("@"))
                    {
                        String[] args = input.split(" ", 2);
                        String target = args[0].substring(1, args[0].length());
                        String content = args[1];
                        sendDM(target, content);
                    }
                    else if (input.startsWith("/"))
                    {
                        String command = input.substring(1, input.length());
                        sendCommand(command);
                    }
                    else
                        sendAll(input);
                }
            }
            else
            {
                System.out.println(response.getResponse());
                authenticate();
            }
        }
    }

    public void sendDM(String target, String content) {
        if (!client.isConnected())
        {
            System.out.println("Message error: Client not connected");
            return;
        }
        
        Sendable message = new Sendable();
        message.setType(Constants.TYPE_MESSAGE);
        message.setTarget(target);
        message.setUser(this.username);
        message.setContent(content);

        this.write.writeToStream(message);
    }

    public void sendAll(String content) {
        if (!client.isConnected())
        {
            System.out.println("Message error: Client not connected");
            return;
        }
        Sendable message = new Sendable();

        message.setType(Constants.TYPE_MESSAGE);
        message.setTarget("*");
        message.setUser(this.username);
        message.setContent(content);

        this.write.writeToStream(message);
    }

    public void sendCommand(String commandName)
    {
        if (!client.isConnected())
        {
            System.out.println("Message error: Client not connected");
            return;
        }

        if (commandName.equals("disconnect"))
        {
            disconnect();
            return;
        }

        Sendable command = new Sendable();

        command.setType(Constants.TYPE_COMMAND);
        command.setUser(this.username);
        command.setContent(commandName);

        this.write.writeToStream(command);
    }

    public void disconnect()
    {
        Sendable disconnection = new Sendable();
        disconnection.setType(Constants.TYPE_NOTIFICATION);
        disconnection.setUser(this.username);
        disconnection.setAction(Constants.ACTION_DISCONNECT);
        write.writeToStream(disconnection);
        try { this.client.close(); } catch (Exception e) {}
        System.out.println("You've been disconnected from the chatroom");
    }

    public Socket getClient() {
        return this.client;
    }
}
