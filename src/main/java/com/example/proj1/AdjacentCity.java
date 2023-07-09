package com.example.proj1;

class AdjacentCity {
    private String name;
    private int petrolCost;
    private int hotelCost;
    public int distance;

    public AdjacentCity(String name, int petrolCost, int hotelCost) {
        this.name = name;
        this.petrolCost = petrolCost;
        this.hotelCost = hotelCost;
        this.distance = getDistance();
    }

    public AdjacentCity(String name, int distance) {
        this.name = name;
        this.distance = distance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPetrolCost(int petrolCost) {
        this.petrolCost = petrolCost;
    }

    public void setHotelCost(int hotelCost) {
        this.hotelCost = hotelCost;
    }

    public int getDistance() {
        return hotelCost + petrolCost;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public int getPetrolCost() {
        return petrolCost;
    }

    public int getHotelCost() {
        return hotelCost;
    }
    public int getDistanceTo(AdjacentCity destinationCity) {
        return this.distance + destinationCity.getDistance();
    }

}
