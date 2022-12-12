package com.itismeucci;

import java.util.*;
import java.io.*;
import java.net.*;

public class Client {
    private Socket client;
    private WriteThread write;
    private ReadThread read;
    private Scanner keyboard = new Scanner(System.in);

    public Socket connect(String address, int port) throws IOException, InterruptedException {
        try {
            this.client = new Socket(address, port);
            this.read = new ReadThread(new DataInputStream(client.getInputStream()));
            this.write = new WriteThread(new DataOutputStream(client.getOutputStream()));
            read.start();
            write.start();
            authenticate();
            System.out.println("@<name> to send a Direct Message");
            System.out.println("/<command> to use commands");
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
        if (response.getType().equals(Constants.TYPE_RESPONSE))
        {
            if (response.getStatus() == Constants.STATUS_VALID)
            {
                System.out.println("You've successfully connected to the chatroom!");
            }
            else
            {
                System.out.println(response.getResponse());
                authenticate();
            }
        }
    }

    public Sendable sendDM(String target, String content) {
        if (!client.isConnected())
        {
            System.out.println("Message error: Client not connected");
            return null;
        }
        return new Sendable();
    }

    public Sendable sendAll() {
        if (!client.isConnected())
        {
            System.out.println("Message error: Client not connected");
            return null;
        }
        return new Sendable();
    }

    public Socket getClient() {
        return this.client;
    }
}
