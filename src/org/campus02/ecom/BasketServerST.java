package org.campus02.ecom;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BasketServerST {
    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(1234)){

            while (true) {

                System.out.println("waiting for client");
                Socket client = server.accept();
                System.out.println("client accepted");

                EcommerceLogic ecommerceLogic = new EcommerceLogic(client);
                Thread th = new Thread(ecommerceLogic);
                th.start();
                System.out.println("Thread started");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
