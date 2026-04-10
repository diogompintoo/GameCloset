package com.gamecloset;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import com.google.gson.JsonArray;

public class BarcodeLookup {
    public static final String API_URL = "https://api.upcitemdb.com/prod/trial/lookup?upc=";

    public static GameLookup barcodeSearch(String barcode) {
        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(15))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + "barcode=" + barcode.trim()))
                    .timeout(Duration.ofSeconds(15))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 && response.body().contains("\"name\"")) {
                Gson gson = new Gson();

                JsonArray array = gson.fromJson(response.body(), JsonArray.class);

                if (array.size() > 0) {
                    JsonObject json = array.get(0).getAsJsonObject();

                    String title = json.get("name").getAsString();
                    String platform = json.has("platform")
                            ? json.get("platform").getAsString()
                            : "Unknown";

                    return new GameLookup(title, platform);
                }
            }
            return null;

        } catch (IOException | InterruptedException e) {
            System.out.println("Error connecting to API: " + e.getMessage());
            return null;
        }
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