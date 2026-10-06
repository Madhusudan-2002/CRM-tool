package com.crm.crmtool.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, String> home() {

        Map<String, String> response = new HashMap<>();

        response.put("message", "CRM Tool Backend API is running");
        response.put("status", "UP");
        response.put("version", "v1");

        return response;
    }
}
