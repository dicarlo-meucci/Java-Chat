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

    public void writeToStream(String data)
    {
        try {
            this.out.writeBytes(data + '\0');
        } catch (Exception e)
        {
            System.out.println("Error while trying to write on stream: " + e.getMessage());
        }
    }

    public DataOutputStream getOut() {
        return this.out;
    }

    public void setOut(DataOutputStream out) {
        this.out = out;
    }

}
