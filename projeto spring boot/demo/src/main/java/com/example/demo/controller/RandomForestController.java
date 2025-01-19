package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.RandomForestService;

@RestController
@RequestMapping("/api/ml")
public class RandomForestController {
    @Autowired
    private RandomForestService randomForestService;

    @PostMapping("/trainWithLanguage")
    public ResponseEntity<String> trainModelWithLanguageId(@RequestParam(name = "languageId") int languageId) {
        System.out.println("Receive language id: " + languageId);
        try {
            this.randomForestService.trainTest(languageId);
            return ResponseEntity.ok("Model trained with language id " + languageId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error training the model");
        }
    }

    @PostMapping("/predict")
    public ResponseEntity<int[]> predict(@RequestBody double[][] features) {
        try {
            return ResponseEntity.ok(this.randomForestService.predict(features, 1));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveModel(@RequestBody String filename) {
        try {
            this.randomForestService.saveModel(filename);
            return ResponseEntity.ok("Model saved");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving the model");
        }
    }

    @GetMapping("/load")
    public ResponseEntity<String> loadModel(@RequestParam(name = "cursoId") Long cursoId) {
        System.out.println("Receive curso id: " + cursoId);
        try {
            this.randomForestService.loadModel(cursoId);
            return ResponseEntity.ok("Model loaded " + cursoId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error loading the model");
        }
    }
}
