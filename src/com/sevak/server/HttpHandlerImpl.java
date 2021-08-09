package com.sevak.server;

import com.google.gson.Gson;
import com.sevak.model.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HttpHandlerImpl implements HttpHandler {

    Map<Integer, User> userMap = new HashMap<>();


    @Override
    public void handle(HttpExchange exchange) {
        String requestHeader = exchange.getRequestMethod();
        switch (requestHeader){
            case "GET":
                answer(exchange, handlerGetRequest());
                break;
            case "POST":
                answer(exchange, handlerPostRequest(exchange.getRequestBody()));
                break;
            case "PUT":
                answer(exchange, handlerPutRequest(exchange.getRequestBody()));
                break;
            case "DELETE":
                answer(exchange, handlerDeleteRequest(exchange.getRequestURI().toString()));
                break;
            default:
                answer(exchange, "ONLY GET POST PUT DELETE");
        }
        
    }

    //ANSWER for CASE
    private void answer(HttpExchange exchange, String answer){
        try {
            exchange.sendResponseHeaders(200, answer.length());
            OutputStream out = exchange.getResponseBody();
            out.write(answer.getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //GET
    private String handlerGetRequest(){
        Gson gson = new Gson();

        StringBuilder users = new StringBuilder();
        for (User user : userMap.values()) {
            users.append(gson.toJson(user)).append("\n");
        }
        return users.toString();
    }

    //POST
    private String handlerPostRequest(InputStream inputStream){
        Gson gson = new Gson();
        User user = gson.fromJson(getBodyData(inputStream), User.class);
        if(userMap.containsKey(user.getId())){
            return "USER ALREADY EXIST";
        }else {
            userMap.put(user.getId(), user);
            return "OK";
        }
    }

    //PUT
    private String handlerPutRequest(InputStream inputStream){

        Gson gson = new Gson();
        User user = gson.fromJson(getBodyData(inputStream), User.class);
        if(!userMap.containsKey(user.getId())){
            return "USER DON'T EXIST";
        }else {
            userMap.put(user.getId(), user);
            return "OK";
        }
    }

    //DELETE
    private String handlerDeleteRequest(String uri){
        try{
            int id = Integer.parseInt(uri.substring(7));
            if(userMap.containsKey(id)){
                userMap.remove(id);
                return "OK";
            }else {
                return "USER DON'T EXIST";
            }
        }catch (Exception e){
            return "URL is incorrect";
        }
    }

    //BODY
    private String getBodyData(InputStream inputStream){
        StringBuilder bodyData = new StringBuilder();
        Reader reader = new InputStreamReader(inputStream);
        try {
            int c;
            while ((c = reader.read()) != -1) {
                bodyData.append((char) c);
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return String.valueOf(bodyData);
    }
}

