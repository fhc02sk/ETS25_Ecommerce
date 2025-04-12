package org.campus02.ecom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BasketAnalyzer {
    private ArrayList<BasketData> baskets;

    public BasketAnalyzer(ArrayList<BasketData> baskets) {
        this.baskets = baskets;
    }

    public ArrayList<BasketData> getEveryNthBasket(int n) {

        ArrayList<BasketData> result = new ArrayList<>();

        for (int i = 0; i < baskets.size(); i+=n) {

            result.add(baskets.get(i));
        }
        return result;
    }

    public ArrayList<BasketData> filterBaskets(String paymentType, Double from, Double to) {

        ArrayList<BasketData> result = new ArrayList<>();
        for (BasketData bd : baskets) {
            if (bd.getPaymentType().equals(paymentType) &&
                    bd.getOrderTotal() >= from && bd.getOrderTotal() <= to) {
                result.add(bd);
            }
        }
        return result;
    }

    public HashMap<String,ArrayList<Double>> groupByProductCategory() {

        HashMap<String,ArrayList<Double>> result = new HashMap<>();
        for (BasketData bd : baskets) {
            if (result.containsKey(bd.getProductCategory())) {
                ArrayList<Double> list = result.get(bd.getProductCategory());
                list.add(bd.getOrderTotal());
            }
            else {
                ArrayList<Double> list = new ArrayList<>();
                list.add(bd.getOrderTotal());
                result.put(bd.getProductCategory(), list);
            }
        }
        return result;
    }
}
