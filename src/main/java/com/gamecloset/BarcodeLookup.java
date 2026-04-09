package com.gamecloset;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class BarcodeLookup {
    public static final String API_URL = "https://levelcomplete.de/api/public/search.php?";

    public static GameLookup barcodeSearch(String barcode) {
        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + barcode.trim()))
                    .timeout(Duration.ofSeconds(10))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 && response.body().contains("\"name\"")) {
                Gson gson = new Gson();
                JsonObject json = gson.fromJson(response.body(), JsonObject.class);

                String title = json.get("name").getAsString();
                String platform = json.get("platform").getAsString();

                GameLookup gameLookup = new GameLookup(title, platform);
                return gameLookup;
            }
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
