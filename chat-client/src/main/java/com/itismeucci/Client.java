package com.itismeucci;

import java.util.*;
import java.io.*;
import java.net.*;

public class Client {
    private Socket client;
    private String address;
    private int port;
    private DataInputStream in;
    private Scanner keyboard = new Scanner(System.in);
    private String userString;


    public Socket connect(String address, int port) throws IOException {
        try {
            this.client = new Socket(address, port);
            this.in = new DataInputStream(client.getInputStream());
        } catch (Exception e)
        {
            System.out.println("Errore durante la connessione: " + e.getLocalizedMessage());
        }

        return this.client;
    }

    public Socket getClient()
    {
        return this.client;
    }
}
