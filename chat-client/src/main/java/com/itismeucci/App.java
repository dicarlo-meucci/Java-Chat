package com.itismeucci;

import java.io.File;
import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

public class App {
    public static void main(String[] args) {
        File configFile = new File("client-config.json");
        ObjectMapper objectMapper = new ObjectMapper();
        if (configFile.exists()) {
            try {
                String content = FileUtils.readFileToString(configFile, StandardCharsets.UTF_8);
                System.out.println(content);
                Config config = objectMapper.readValue(content, Config.class);
                Client client = new Client();
                while (client.getClient() == null)
                {
                    try {
                        client.connect(config.getAddress(), config.getPort());
                    } catch (Exception e)
                    {
                        System.out.println(e);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
