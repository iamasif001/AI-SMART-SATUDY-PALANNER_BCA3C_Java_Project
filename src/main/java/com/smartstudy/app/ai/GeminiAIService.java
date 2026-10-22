
package com.smartstudy.app.ai;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class GeminiAIService {

    private static final String MODEL = "gemini-3.8-flash";

    public String generateMCQs(String studyMaterial, int numberOfQuestions)
            throws Exception {

        String apiKey = System.getenv("GEMINI_API_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "GEMINI_API_KEY environment variable not found."
            );
        }

        if (studyMaterial == null || studyMaterial.isBlank()) {
            throw new IllegalArgumentException(
                    "Study material text is empty."
            );
        }

        String prompt =
                "Create " + numberOfQuestions
                + " multiple-choice questions based ONLY on the study material below. "
                + "For each question, provide four options A, B, C, D, "
                + "the correct answer, and a short explanation. "
                + "Do not introduce facts that are not supported by the material.\n\n"
                + "STUDY MATERIAL:\n"
                + studyMaterial;

        String requestBody = """
                {
                  "contents": [{
                    "parts": [{
                      "text": %s
                    }]
                  }]
                }
                """.formatted(toJsonString(prompt));

       
HttpClient client = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(20))
        .build();

for (int attempt = 1; attempt <= 3; attempt++) {

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(
                    "https://generativelanguage.googleapis.com/v1beta/models/"
                            + MODEL + ":generateContent"))
            .timeout(Duration.ofSeconds(90))
            .header("Content-Type", "application/json")
            .header("x-goog-api-key", apiKey)
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

    HttpResponse<String> response = client.send(
            request,
            HttpResponse.BodyHandlers.ofString()
    );

    if (response.statusCode() == 200) {
        return extractResponseText(response.body());
    }

    int statusCode = response.statusCode();

    if ((statusCode == 429 || statusCode == 500
            || statusCode == 502 || statusCode == 503
            || statusCode == 504) && attempt < 3) {

        Thread.sleep(attempt * 3000L);
        continue;
    }

    throw new RuntimeException(
            "Gemini API error (HTTP "
                    + statusCode + "): "
                    + response.body()
    );
}

throw new RuntimeException(
        "Gemini API is temporarily unavailable. Please try again later."
);

    }

    private String toJsonString(String text) {
        StringBuilder result = new StringBuilder("\"");

        for (char ch : text.toCharArray()) {
            switch (ch) {
                case '"':
                    result.append("\\\"");
                    break;
                case '\\':
                    result.append("\\\\");
                    break;
                case '\n':
                    result.append("\\n");
                    break;
                case '\r':
                    result.append("\\r");
                    break;
                case '\t':
                    result.append("\\t");
                    break;
                default:
                    if (ch < 0x20) {
                        result.append(String.format("\\u%04x", (int) ch));
                    } else {
                        result.append(ch);
                    }
            }
        }

        result.append('"');
        return result.toString();
    }

    private String extractResponseText(String json) {
        String marker = "\"text\":";
        int markerIndex = json.indexOf(marker);

        if (markerIndex == -1) {
            throw new RuntimeException(
                    "No generated text found in Gemini response."
            );
        }

        int start = json.indexOf('"', markerIndex + marker.length());

        if (start == -1) {
            throw new RuntimeException(
                    "Could not read generated text from Gemini response."
            );
        }

        StringBuilder result = new StringBuilder();
        boolean escaped = false;

        for (int i = start + 1; i < json.length(); i++) {
            char ch = json.charAt(i);

            if (escaped) {
                switch (ch) {
                    case 'n':
                        result.append('\n');
                        break;
                    case 'r':
                        result.append('\r');
                        break;
                    case 't':
                        result.append('\t');
                        break;
                    case '"':
                        result.append('"');
                        break;
                    case '\\':
                        result.append('\\');
                        break;
                    case '/':
                        result.append('/');
                        break;
                    case 'b':
                        result.append('\b');
                        break;
                    case 'f':
                        result.append('\f');
                        break;
                    case 'u':
                        if (i + 4 < json.length()) {
                            String hex = json.substring(i + 1, i + 5);
                            result.append((char) Integer.parseInt(hex, 16));
                            i += 4;
                        }
                        break;
                    default:
                        result.append(ch);
                }

                escaped = false;
            } else if (ch == '\\') {
                escaped = true;
            } else if (ch == '"') {
                return result.toString();
            } else {
                result.append(ch);
            }
        }

        throw new RuntimeException(
                "Generated text ended unexpectedly."
        );
    }
}
