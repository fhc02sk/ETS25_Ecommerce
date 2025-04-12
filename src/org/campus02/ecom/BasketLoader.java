package org.campus02.ecom;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BasketLoader {

    public static ArrayList<BasketData> load(String path, Comparator<BasketData> comparator) throws DataFileException {

        ArrayList<BasketData> result = load(path);
        Collections.sort(result, comparator);
        return result;
    }

    public static ArrayList<BasketData> load(String path) throws DataFileException {

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            ArrayList<BasketData> result = new ArrayList<>();

            String line;
            while ((line = br.readLine()) != null) {

                BasketData bd = new Gson().fromJson(line, BasketData.class);
                result.add(bd);
            }

            return result;

        } catch (FileNotFoundException e) {
            throw new DataFileException(path, e);
        } catch (IOException e) {
            throw new DataFileException(path, e);
        }


    }

}
