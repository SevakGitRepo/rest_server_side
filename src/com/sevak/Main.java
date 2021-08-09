package com.sevak;

import com.sevak.server.HttpHandlerImpl;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args)  {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
            server.createContext("/users", new HttpHandlerImpl());
            server.start();
            System.out.println("Server started! in port 8080 . . . ");
        }catch (IOException e){
            System.out.println("Something get wrong");
            e.printStackTrace();
        }

    }
}
