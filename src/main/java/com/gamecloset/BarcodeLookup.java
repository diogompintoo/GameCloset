package com.gamecloset;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class BarcodeLookup {

    public static final String API_URL = "https://api.upcitemdb.com/prod/trial/lookup?upc=";

    public static GameLookup barcodeSearch(String barcode) {
        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + barcode.trim()))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject jsonResponse = new Gson().fromJson(response.body(), JsonObject.class);
                JsonArray items = jsonResponse.getAsJsonArray("items");

                if (items != null && items.size() > 0) {
                    JsonObject item = items.get(0).getAsJsonObject();
                    String title = item.get("title").getAsString();
                    String platform = item.has("category") ? item.get("category").getAsString() : "Unknown";

                    return new GameLookup(title, platform);
                }
            }
        } catch (Exception e) {
            System.out.println("API error: " + e.getMessage());
        }
        return null;
    }

    static class GameLookup {
        String title;
        String platform;
        public GameLookup(String title, String platform) {
            this.title = title;
            this.platform = platform;
        }
    }
}