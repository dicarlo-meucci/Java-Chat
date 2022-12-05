package com.itismeucci;

import java.util.*;
import java.io.*;
import java.net.*;

public class Client {
    private Socket client;
    private String address;
    private int port;
    private WriteThread write;
    private ReadThread read;
    private Scanner keyboard = new Scanner(System.in);
    private String userString;

    public Socket connect(String address, int port) throws IOException, InterruptedException {
        try {
            this.client = new Socket(address, port);
            this.read = new ReadThread(new DataInputStream(client.getInputStream()));
            this.write = new WriteThread(new DataOutputStream(client.getOutputStream()));
            read.start();
            write.start();
        } catch (Exception e) {
            System.out.println("Errore durante la connessione: " + e.getMessage());
            Thread.sleep(3000);
        }

        return this.client;
    }

    private Sendable authenticate() {
        return new Sendable();

    }

    private Sendable sendDM() {
        return new Sendable();
    }

    private Sendable sendAll() {

        return new Sendable();
    }

    public Socket getClient() {
        return this.client;
    }
}
