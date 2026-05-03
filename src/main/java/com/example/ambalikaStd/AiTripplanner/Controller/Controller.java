package com.example.ambalikaStd.AiTripplanner.Controller;

import com.example.ambalikaStd.AiTripplanner.Service.APIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
    @RequestMapping("/api/trip")
    @CrossOrigin(origins = "*")
    public class Controller {

        @Autowired
        private APIService geminiAPIService;

        @PostMapping("/plan")
        public String getTripPlan(@RequestBody Map<String, String> request) {
            String userPrompt = request.get("prompt");
            String systemPrompt = request.get("systemPrompt");

            // Dono ko combine karke AI ko bhejte hain
            String fullPrompt = systemPrompt + "\n\nUser Question: " + userPrompt;
            return geminiAPIService.callAI(fullPrompt);
        }
    }

