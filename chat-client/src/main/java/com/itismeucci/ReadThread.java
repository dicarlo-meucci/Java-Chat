package com.itismeucci;
import java.io.*;
import java.net.*;
import java.util.*;

public class ReadThread extends Thread {
    private Socket client;
    private DataInputStream in;
    private Scanner keyboard = new Scanner(System.in);
    private String userString;

public Socket connect() throws IOException
    {
        this.client= new Socket(InetAddress.getLocalHost(), 34567);
        this.in = new DataInputStream(client.getInputStream());
       
        return client;
    }
    @Override
    public void run()
    {
        
    }
}