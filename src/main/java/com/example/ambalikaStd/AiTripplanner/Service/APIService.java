package com.example.ambalikaStd.AiTripplanner.Service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
public class APIService {

    Dotenv dotenv = Dotenv.load();
    String API_KEY = dotenv.get("GROQ_API_KEY");
    private final String URL = "https://api.groq.com/openai/v1/chat/completions";

    public String callAI(String prompt) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> body = Map.of(
                "model", "llama-3.3-70b-versatile",
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                )
        );

        HttpHeaders headers = new HttpHeaders();
        // FIXED: Changed APPLICATION_APPLICATION_JSON to APPLICATION_JSON
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            Map<String, Object> response = restTemplate.postForObject(URL, entity, Map.class);

            if (response != null && response.containsKey("choices")) {
                List choices = (List) response.get("choices");
                Map firstChoice = (Map) choices.get(0);
                Map message = (Map) firstChoice.get("message");
                return message.get("content").toString();
            }
            return "Groq ne response diya par data nahi mila.";
        } catch (Exception e) {
            return "Groq API Error: " + e.getMessage();
        }
    }
}