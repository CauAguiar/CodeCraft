package com.example.demo.service;

import java.io.IOException;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.DataFetchRepository;
import com.example.demo.repository.RandomForestRepository;

import smile.classification.RandomForest;
import smile.data.DataFrame;
import smile.data.formula.Formula;
import smile.data.vector.IntVector;

@Service
public class RandomForestService {
    @Autowired
    private DataFetchRepository dataFetchRepository;

    @Autowired
    private RandomForestRepository randomForestRepository;
    
    private RandomForest model;

    public String trainTest(int languageID) {
        double[][] features = this.dataFetchRepository.fetchFeatures(languageID);
        int[] labels = this.dataFetchRepository.fetchLabels(languageID);

        if (features.length == 0 || labels.length == 0) {
            //logger.warn("No data available");
            return "No data available";
        }
        train(languageID, features, labels);
        return "Model trained";
    }

    // Train the model
    public void train(int languageId, double[][] features, int[] labels) {
        String[] featureNames = createFeatureNames(features[0].length);
        this.model = RandomForest.fit(Formula.lhs("nivel"), DataFrame.of(features, featureNames).merge(IntVector.of("nivel", labels)));
    }

    // Predict the class of a new instance
    public int[] predict(double[][] features, long cursoId) {
        this.model = loadModel(cursoId);
        if (this.model == null) {
            throw new IllegalStateException("Model not trained");
        }
        return this.model.predict(DataFrame.of(features, createFeatureNames(features[0].length)));
    }

    // Save the model to a file
    public void saveModel(String filename) {
        try {
            this.randomForestRepository.saveModel(this.model, filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Load the model from a file
    public RandomForest loadModel(Long cursoID) {
        try {
            return this.model = this.randomForestRepository.loadModel(cursoID);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String[] createFeatureNames(int numFeatures) {
        return IntStream.range(0, numFeatures).mapToObj(i -> "resposta" + (i + 1)).toArray(String[]::new);
    }
}
