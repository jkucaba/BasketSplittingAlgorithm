package com.ocado.basket;

import java.io.IOException;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BasketSplitter {
    // Tutaj będziemy wczytywać plik z Config file Kluczem Mapy będzie Produkt, natomiast wartością będzie lista sposobów dostawy danego produktu
    private Map<String, List<String>> productDeliveryMap = new HashMap<>();

    // Ta funckja służy do wczytania z pliku produktów oraz zapisania ich do hashMapy
    public BasketSplitter(String absolutePathToConfigFile) {
        try {
            // Czytamy plik JSON jako string
            String content = new String(Files.readAllBytes(Paths.get(absolutePathToConfigFile)));

            // Parsujemy go na JSONObject
            JSONObject jsonObject = new JSONObject(content);

            //Sprawdzamy czy nie jest więcej niż 1000 produktów - założenie zadania
            if (jsonObject.length() > 1000) {
                throw new Exception("More than 1000 products");
            }

            // klucz to nazwa produktu, natomiast sposoby dostawy zamieniamy na listę
            for (String productName : jsonObject.keySet()) {
                JSONArray deliveryArray = jsonObject.getJSONArray(productName);

                //Sprawdzamy czy jest więcej niż 10 typów dostaw - założenie zadania
                if (deliveryArray.length() > 10) {
                    throw new Exception("More than 10 delivery types");
                }

                // Zamieniamy JSONArray na List<String>
                List<String> deliveryTypes = new ArrayList<>();
                for (int i = 0; i < deliveryArray.length(); i++) {
                    deliveryTypes.add(deliveryArray.getString(i));
                }

                // Dodajemy produkt i sposoby dostawy do mapy
                productDeliveryMap.put(productName, deliveryTypes);

            }
            // Wypisujemy mapę
/*            for (Map.Entry<String, List<String>> entry : productDeliveryMap.entrySet()) {
                System.out.print("Product: " + entry.getKey());
                System.out.print("Delivery Types: " + entry.getValue());
                System.out.println();
            }*/
        }catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (JSONException e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, List<String>> split(List<String> items) {
        if(items.size() > 100){
            throw new IllegalArgumentException("Too many items");
        }

        // Tutaj będziemy zapisywać który sposób dostawy ma największą ilość produktów z możliwością ich dostawy
        Map<String, Integer> deliveryTypeCounts = new HashMap<>();

        // Iterujemy przez każdy produkt i jego listę dostaw
        for (String productName : items) {

            // Lista dostaw danego produktu
            List<String> deliveryTypes = productDeliveryMap.get(productName);
            if (deliveryTypes != null) {
                // inkrementujemy licznik każdej z dostaw
                for (String deliveryType : deliveryTypes) {
                    deliveryTypeCounts.put(deliveryType, deliveryTypeCounts.getOrDefault(deliveryType, 0) + 1);
                }
            }
        }

        // Wypisanie sposobów dosatwy i ich licznika
/*
        for (Map.Entry<String, Integer> entry : deliveryTypeCounts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
*/

        // Result będzie mapą, którą zwrócimy
        Map<String, List<String>> result = new HashMap<>();

        // Sortujemy liste coutns
        List<Map.Entry<String, Integer>> sortedCounts = new ArrayList<>(deliveryTypeCounts.entrySet());
        sortedCounts.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        for (Map.Entry<String, Integer> entry : sortedCounts) {
            result.put(entry.getKey(), new LinkedList<>());
        }

        // Iterujemy po produktach i dodajemy do typu dostawy z największą ilością produktów
        for (String productName : items) {
            // Rodzaje dostawy dla wybranego produktu
            List<String> deliveryTypes = productDeliveryMap.get(productName);
            if (deliveryTypes != null) {
                // Znajdź typ dostawy z największą wartością licznika wymienioną w typach dostawy produktu
                for (Map.Entry<String, Integer> countEntry : sortedCounts) {
                    String deliveryType = countEntry.getKey();
                    if (deliveryTypes.contains(deliveryType)) {
                        // Dodaj produkt do rodzaju dostawy z największą wartością licznika
                        if (!result.containsKey(deliveryType)) {
                            result.put(deliveryType, new LinkedList<>());
                        }
                        result.get(deliveryType).add(productName);
                        break;
                    }
                }
            }
        }
/*        System.out.println("\nProducts by Delivery Type:");
        for (Map.Entry<String, List<String>> entry : result.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }*/
        return result;
    }

    public static void main(String[] args) {
        BasketSplitter bask = new BasketSplitter("");
        List<String> lista = List.of("Fond - Chocolate", "Chocolate - Unsweetened", "Nut - Almond, Blanched, Whole", "Haggis", "Mushroom - Porcini Frozen", "Cake - Miini Cheesecake Cherry", "Sauce - Mint", "Longan", "Bag Clear 10 Lb", "Nantucket - Pomegranate Pear", "Puree - Strawberry", "Numi - Assorted Teas", "Apples - Spartan", "Garlic - Peeled", "Cabbage - Nappa", "Bagel - Whole White Sesame", "Tea - Apple Green Tea");
        bask.split(lista);

    }

}
