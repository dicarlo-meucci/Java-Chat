package com.itismeucci;
import java.io.DataOutputStream;

public class WriteThread extends Thread {
    private DataOutputStream out;

    public WriteThread(DataOutputStream out)
    {
        this.out = out;
    }

    @Override
    public void run()
    {
        System.out.println("INZIALIZZATA STREAM OUTPUT");
    }

    public Sendable writeStream()
    {
        
    }

    public DataOutputStream getOut() {
        return this.out;
    }

    public void setOut(DataOutputStream out) {
        this.out = out;
    }

}
