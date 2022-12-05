package com.itismeucci;
import java.io.*;

public class ReadThread extends Thread {
    private DataInputStream in;

    public ReadThread(DataInputStream in)
    {
        this.in = in;
    }

    @Override
    public void run()
    {
        System.out.println("INZIALIZZATA STREAM INPUT");
    }

    private Sendable readStream()
    {
        return new Response();
    }

    public DataInputStream getIn() {
        return this.in;
    }

    public void setIn(DataInputStream in) {
        this.in = in;
    }

}
