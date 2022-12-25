package com.itismeucci;

import java.io.File;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

public class App 
{
    public static void main( String[] args )
    {
        int port = 8080;
        if (args.length > 0)
        {
            if (args[0].equals("-p"))
            {
                port = Integer.parseInt(args[1]);
            }
        }

        System.out.println("Server opened on port " + port);

        File configFile = new File("server-config.json");
        ObjectMapper objectMapper = new ObjectMapper();
        if (configFile.exists()) {
            try {
                String content = FileUtils.readFileToString(configFile, StandardCharsets.UTF_8);
                System.out.println(content);
                Config config = objectMapper.readValue(content, Config.class);
                Server.configure(config.getPort());
                Server.listen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
