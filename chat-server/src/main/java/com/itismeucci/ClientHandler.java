package com.itismeucci;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class ClientHandler extends Thread {
    private Socket client;
    private Scanner scanner;
    private DataInputStream in;
    private DataOutputStream out;

    public ClientHandler(Socket client) {
        this.client = client;
        try {
            this.in = new DataInputStream(client.getInputStream());
            this.out = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {

            e.printStackTrace();
        }
        this.scanner.useDelimiter("\0");
        System.out.println("Client connesso");
    }

    public void writeToStream(Sendable data) {
        try {
            String obj = Formatter.serialize(data);
            this.out.writeBytes(obj + '\0');
        } catch (Exception e) {
            System.out.println("Error while trying to write on stream: " + e.getMessage());
        }
    }

    public Sendable readFromStream() {
        String data = this.scanner.next();
        try {
            Sendable obj = Formatter.deserialize(data);
            return obj;
        } catch (Exception e)
        {
            Sendable response = new Sendable();
            response.setStatus(Constants.STATUS_INVALID);
            response.setResponse(Constants.RESPONSE_INVALID);

        }
        return null;
    }

    @Override
    public void run() {
        while (true) {
            Sendable obj = readFromStream();

            if (obj == null)
            return;

            switch (obj.getType()) {
                case Constants.TYPE_MESSAGE:
                    break;
                case Constants.TYPE_NOTIFICATION:
                if (obj.getAction().equals(Constants.ACTION_CONNECT))
                {
                    Server.authenticate(obj, this);
                }
                    break;
                case Constants.TYPE_RESPONSE:
                    break;
                case Constants.TYPE_COMMAND:
                    break;
            }
        }
    }
}
