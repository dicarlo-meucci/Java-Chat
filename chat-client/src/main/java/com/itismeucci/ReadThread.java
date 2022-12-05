package com.itismeucci;
import java.io.*;
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
        System.out.println("INZIALIZZATA STREAM INPUT");
    }

    public Sendable readFromStream()
    {
        String data = this.scanner.next();
        Sendable obj = Formatter.deserialize(data);
        return obj;
    }

    public DataInputStream getIn() {
        return this.in;
    }

    public void setIn(DataInputStream in) {
        this.in = in;
    }

}
