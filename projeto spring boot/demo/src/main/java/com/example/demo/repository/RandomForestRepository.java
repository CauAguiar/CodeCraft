package com.example.demo.repository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import smile.classification.RandomForest;

@Repository
public class RandomForestRepository {
    public void saveModel(RandomForest model, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(model);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RandomForest loadModel(Long cursoID) throws IOException {
        String filePath = String.format("final_model%d.ser", cursoID); // Example: return a file path for cursoID
        try (InputStream inputStream = new ClassPathResource(filePath).getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            return (RandomForest) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}