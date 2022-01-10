package com.example.test1.demo.client;

import java.io.IOException;

public class AClient {

    public static void main(String[] args) {
        try {
            new com.example.test1.demo.client.ChatClient().startClient("lucy", "1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
