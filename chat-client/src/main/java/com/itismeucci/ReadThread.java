package com.itismeucci;
import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ReadThread extends Thread {
    private DataInputStream in;
    private Scanner scanner;
    
    public ReadThread(DataInputStream in)
    {
        this.in = in;
        this.scanner = new Scanner(this.in);
        this.scanner.useDelimiter("\0");
    }

    @Override
    public void run()
    {
        while (true)
        {
            try {Thread.sleep(10);} catch (Exception e) {} // if removed, this breaks everything
            if (Client.isAuthenticated)
            {
                Sendable obj = readFromStream();

                if (obj == null)
                continue;
                if (obj.getStatus() == Constants.STATUS_VALID || obj.getStatus() == 0)
                {
                    if (obj.getType().equals(Constants.TYPE_MESSAGE))
                    {
                        if (obj.getTarget().equals("*"))
                        {
                            System.out.println(obj.getUser() + ": " + obj.getContent());
                        }
                        else
                        System.out.println("[DM] " + obj.getUser() + ": " + obj.getContent());

                        continue;
                    }
                    if (obj.getType().equals(Constants.TYPE_COMMAND_RESPONSE))
                    {
                        String cmdString = "\nConnected clients " + "(" + obj.getParticipants().size() + "):\n";
                        for (String participant : obj.getParticipants())
                        {
                            cmdString += participant + '\n';
                        }

                        System.out.println(cmdString);
                        continue;
                    }
                    if (obj.getType().equals(Constants.TYPE_NOTIFICATION))
                    {
                        System.out.println("[NOTIFICATION] " + obj.getUser() + " " + obj.getAction() + "ed");
                    }
                }
                else
                System.out.println(obj.getResponse());
            }
        }
    }

    public Sendable readFromStream()
    {
        String data = "";
        try {
            data = this.scanner.next();
        } catch (NoSuchElementException e)
        {
            Runtime.getRuntime().halt(0);
        }
        Sendable incomingObject;

        if (data.isEmpty())
            return null;

        incomingObject = Formatter.deserialize(data);
        return incomingObject;
    }

    public DataInputStream getIn() {
        return this.in;
    }

    public void setIn(DataInputStream in) {
        this.in = in;
    }
}
