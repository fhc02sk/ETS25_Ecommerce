package org.campus02.ecom;

import javax.swing.text.html.parser.Entity;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EcommerceLogic implements Runnable {
    private Socket socket;

    public EcommerceLogic(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {

        try (BufferedReader br = new BufferedReader
                (new InputStreamReader(socket.getInputStream()));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {

            String command  = "";
            BasketAnalyzer ba = null;
            while ((command = br.readLine()) != null) {
                if (command.equals("EXIT"))
                    break;

                String[] commandParts = command.split(" ");

                if (commandParts[0].equals("OpenFile")) {
                    try {
                        ArrayList<BasketData> list = BasketLoader.load(commandParts[1]);
                        ba = new BasketAnalyzer(list);
                        bw.write(list.size() + " entries loaded");
                        bw.newLine();
                        bw.flush();
                    } catch (DataFileException e) {
                        bw.write("Fehler beim Laden der Datei");
                        bw.newLine();
                        bw.flush();
                        e.printStackTrace();
                    }
                }
                else if (commandParts[0].equals("GetEveryNth")) {

                    if (commandParts.length != 2 || ba == null) {
                        bw.write("Calling GetEveryNth without OpenFile");
                        bw.newLine();
                        bw.flush();
                        continue;
                    }

                    int n = Integer.parseInt(commandParts[1]);
                    ArrayList<BasketData> list =  ba.getEveryNthBasket(n);

                    for (BasketData bd : list) {
                        bw.write(bd.toString());
                        bw.newLine();
                    }
                    bw.flush();
                }
                else if (commandParts[0].equals("GetStats")) {
                    if (ba == null) {
                        bw.write("Calling Stats without OpenFile");
                        bw.newLine();
                        bw.flush();
                        continue;
                    }

                    HashMap<String, ArrayList<Double>> map = ba.groupByProductCategory();

                    for (Map.Entry<String, ArrayList<Double>> entry : map.entrySet()){

                        double sum = 0;
                        for (double d : entry.getValue()) {
                            sum += d;
                        }
                        double avg = sum / entry.getValue().size();

                        bw.write(entry.getKey() + " > " + avg);
                        bw.newLine();
                        bw.flush();
                    }
                }
                else {
                    bw.write("Unkown command");
                    bw.newLine();
                    bw.flush();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
