package com.itismeucci;

import java.util.*;
import java.io.*;
import java.net.*;

public class Client {
    private Socket client;
    private DataInputStream in;
    private Scanner keyboard = new Scanner(System.in);
    private String userString;

    public Socket connect() throws IOException {
        this.client = new Socket(InetAddress.getLocalHost(), 34567);
        this.in = new DataInputStream(client.getInputStream());

        return client;
    }
}
