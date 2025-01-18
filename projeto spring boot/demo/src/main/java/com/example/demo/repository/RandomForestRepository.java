package com.example.demo.repository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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

    public RandomForest loadModel(Long cursoId) {
        String filename = "models/final_model" + cursoId + ".ser";
        try (ObjectInputStream ois = new ObjectInputStream(getClass().getClassLoader().getResourceAsStream(filename))) {
            return (RandomForest) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}