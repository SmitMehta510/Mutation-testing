package com.TranquilMind.utilities;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class MLUtility {
    public boolean saveProduct(String desc) {
        // Create RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create request entity
        HttpEntity<String> requestEntity = new HttpEntity<>(desc, headers);

        // Make HTTP POST request to Flask project
        ResponseEntity<Boolean> response = restTemplate.postForEntity("http://flask-project-url/api/process", requestEntity, Boolean.class);

        // Process response
        return response.getBody() != null && response.getBody();
    }
}
