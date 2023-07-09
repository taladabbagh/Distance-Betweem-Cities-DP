package com.example.proj1;

import java.util.ArrayList;
import java.util.List;

public class City {
    private String name;
    private List<AdjacentCity> adjacents;

    public City(String name) {
        this.name = name;
        this.adjacents = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public List<AdjacentCity> getAdjacents() {
        return adjacents;
    }

    public void addAdjacent(AdjacentCity adjacentCity) {
        adjacents.add(adjacentCity);
    }

    public AdjacentCity getAdjacent(String cityName) {
        for (AdjacentCity adjacent : adjacents) {
            if (adjacent.getName().equals(cityName)) {
                return adjacent;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\''
                ;
    }
}
