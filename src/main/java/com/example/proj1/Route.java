package com.example.proj1;

public class Route {
 //   each Route object represents a path from the start city to the end city along with its cost.
    private String path;
    private int cost;

    public Route(String path, int cost) {
        this.path = path;
        this.cost = cost;
    }

    public String getPath() {
        return path;
    }

    public int getCost() {
        return cost;
    }
}
