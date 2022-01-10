package com.example.test1.demo.client;

import java.io.IOException;

public class BClient {

    public static void main(String[] args) {
        try {
            new com.example.test1.demo.client.ChatClient().startClient("mary", "2");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
