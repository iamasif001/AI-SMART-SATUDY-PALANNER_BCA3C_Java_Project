
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class GeminiConnectionTest {

    public static void main(String[] args) {
        String apiKey = System.getenv("GEMINI_API_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            System.out.println("ERROR: API key not found!");
            return;
        }

        try {
            String json = """
                {
                  "contents": [{
                    "parts": [{
                      "text": "Reply with: Gemini connection successful!"
                    }]
                  }]
                }
                """;

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                    "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.8-flash:generateContent"))
                .timeout(Duration.ofSeconds(60))
                .header("Content-Type", "application/json")
                .header("x-goog-api-key", apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

            HttpClient client = HttpClient.newHttpClient();

            HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
            );

            System.out.println("HTTP Status: " + response.statusCode());
            System.out.println("Gemini Response:");
            System.out.println(response.body());

        } catch (Exception e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }
}
