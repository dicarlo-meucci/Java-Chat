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

    }

    public void writeToStream(Sendable data)
    {
        try {
            String obj = Formatter.serialize(data);
            this.out.writeBytes(obj + '\0');
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
