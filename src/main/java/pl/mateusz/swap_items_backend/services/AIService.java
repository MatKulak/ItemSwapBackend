package pl.mateusz.swap_items_backend.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static pl.mateusz.swap_items_backend.others.Constants.AI_API_QUERY;

@Service
public class AIService {

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.api.url}")
    private String apiUrl;

    @Value("${groq.api.model}")
    private String apiModel;

    public String findMatchingAdvertisementsIds(final String myAdvertisements, final String allAdvertisements) {
        try {
            final JSONObject payload = new JSONObject();
            payload.put("model", apiModel);
            payload.put("temperature", 0.5);
            payload.put("max_tokens", 1024);
            payload.put("top_p", 0.65);
            payload.put("stream", false);
            payload.put("stop", JSONObject.NULL);

            final JSONArray messages = new JSONArray();
            final JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", String.format(AI_API_QUERY, myAdvertisements, allAdvertisements) );
            messages.put(message);
            payload.put("messages", messages);

            final HttpClient client = HttpClient.newHttpClient();
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                    .build();

            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                final JSONObject jsonResponse = new JSONObject(response.body());
                return jsonResponse
                        .getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content");
            } else {
                return null;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}