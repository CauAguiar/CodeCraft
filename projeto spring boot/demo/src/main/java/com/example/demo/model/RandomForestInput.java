package com.example.demo.model;

public class RandomForestInput {
    private double[][] features;
    private int[] labels;
    private Integer numTrees = 100;
    private Integer numRandomFeatures = 5;
    private Integer maxNodes = 100;
    private Integer seed = 42;

    public RandomForestInput(double[][] features, int[] labels) {
        this.features = features;
        this.labels = labels;
    }

    public double[][] getFeatures() {
        return features;
    }

    public void setFeatures(double[][] features) {
        this.features = features;
    }

    public int[] getLabels() {
        return labels;
    }

    public void setLabels(int[] labels) {
        this.labels = labels;
    }

    public Integer getNumTrees() {
        return numTrees;
    }

    public void setNumTrees(Integer numTrees) {
        this.numTrees = numTrees;
    }

    public Integer getNumRandomFeatures() {
        return numRandomFeatures;
    }

    public void setNumRandomFeatures(Integer numRandomFeatures) {
        this.numRandomFeatures = numRandomFeatures;
    }

    public Integer getMaxNodes() {
        return maxNodes;
    }

    public void setMaxNodes(Integer maxNodes) {
        this.maxNodes = maxNodes;
    }

    public Integer getSeed() {
        return seed;
    }

    public void setSeed(Integer seed) {
        this.seed = seed;
    }
}
