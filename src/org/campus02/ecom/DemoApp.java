package org.campus02.ecom;

import java.util.ArrayList;

public class DemoApp {

    public static void main(String[] args) {

        try {
            ArrayList<BasketData> list =BasketLoader.load(".\\data\\buyings.json", new BasketComparator());

            for (BasketData bd : list) {
                System.out.println(bd);
            }
            System.out.println(list.size());

        } catch (DataFileException e) {
            throw new RuntimeException(e);
        }


    }

}
