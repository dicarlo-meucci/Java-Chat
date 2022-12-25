package com.itismeucci;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.NoSuchElementException;
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
        this.scanner = new Scanner(this.in);
        this.scanner.useDelimiter("\0");
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
        try {
            String data = this.scanner.next();
            Sendable obj = Formatter.deserialize(data);
            return obj;
        } catch (Exception e)
        {
            if (e instanceof NoSuchElementException)
            {
                Sendable disconnection = new Sendable();
                disconnection.setType(Constants.TYPE_NOTIFICATION);
                disconnection.setUser(getName());
                Server.disconnect(disconnection, this);
            }
        }
        return null;
    }

    @Override
    public void run() {
        while (true) {
            Sendable obj = readFromStream();

            if (obj == null)
            continue;

            switch (obj.getType()) {
                case Constants.TYPE_MESSAGE:
                {
                    Sendable msgResponse;

                    if (obj.getTarget().equals("*"))
                        msgResponse = Server.sendToEveryone(obj);
                    else
                        msgResponse = Server.sendToOne(obj);
    
                        writeToStream(msgResponse);
                        break;
                }
                case Constants.TYPE_NOTIFICATION:
                {
                    if (obj.getAction().equals(Constants.ACTION_CONNECT))
                    {
                        Sendable authResponse = Server.authenticate(obj, this);
                        writeToStream(authResponse);
                        if (authResponse.getStatus() == Constants.STATUS_VALID)
                        Server.notifyEveryone(obj);
                    }
                    else
                    {
                        Server.disconnect(obj, this);
                        Server.notifyEveryone(obj);
                    }
                    break;
                }
                case Constants.TYPE_COMMAND:
                {
                    Sendable commandResponse = Server.command(obj);
                    writeToStream(commandResponse);
                    break;
                }
            }
        }
    }

    public Socket getClient()
    {
        return this.client;
    }
}
