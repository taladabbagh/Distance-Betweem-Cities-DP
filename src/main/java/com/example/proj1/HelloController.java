package com.example.proj1;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class HelloController implements Initializable {

    @FXML
    private TextArea alternTableTA;

    @FXML
    private Button browsebtn;

    @FXML
    private Label costlbl;

    @FXML
    private TextArea dpTableTA;

    @FXML
    private TextField filename;

    @FXML
    private TextArea pathTableTA;

    @FXML
    private Label pathlbl;

    @FXML
    private Button travelbtn;

    @FXML
    private Label pathl;

    @FXML
    private Label costl;
    @FXML
    private Label lbl1;

    @FXML
    private Label lbl2;

    @FXML
    private Label lbl3;

    @FXML
    private Label lbl4;


    private List<City> cities;
    private int numCities;
    private String startCity;
    private String endCity;
    private int[][] dp;
    private int[][] path;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cities = new ArrayList<>(); // Initialize the 'cities' list

        alternTableTA.setVisible(false);
        costlbl.setVisible(false);
        dpTableTA.setVisible(false);
        pathTableTA.setVisible(false);
        pathlbl.setVisible(false);

        browsebtn.setOnAction(event -> handleBrowseButtonAction());
        travelbtn.setOnAction(event -> handleTravelButtonAction());
    }

    private void handleBrowseButtonAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showOpenDialog(filename.getScene().getWindow());
        if (file != null) {
            String filePath = file.getAbsolutePath();
            filename.setText(filePath);
        }
    }

    private void handleTravelButtonAction() {
        String filePath = filename.getText();
        if (!filePath.isEmpty()) {
            cities.clear();
            loadCitiesFromFile(filePath);
            calculateOptimumCost();
            displayResults();
        }
    }

    private void loadCitiesFromFile(String filePath) {
        cities = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            numCities = Integer.parseInt(br.readLine());
            String[] startEnd = br.readLine().split(", ");
            startCity = startEnd[0];
            endCity = startEnd[1];

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");

                String cityName = parts[0];
                City city = null;

                for (City existingCity : cities) {
                    if (existingCity.getName().equals(cityName)) {
                        city = existingCity;
                        break;
                    }
                }

                if (city == null) {
                    city = new City(cityName);
                    cities.add(city);
                }

                for (int i = 1; i < parts.length; i++) {
                    String[] adjacentParts = parts[i].substring(1, parts[i].length() - 1).split(",");
                    String adjacentName = adjacentParts[0];
                    int petrolCost = Integer.parseInt(adjacentParts[1]);
                    int hotelCost = Integer.parseInt(adjacentParts[2]);

                    AdjacentCity adjacentCity = new AdjacentCity(adjacentName, petrolCost, hotelCost);
                    city.addAdjacent(adjacentCity);
                }
            }

            // Add end city
            cities.add(new City(endCity));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void calculateOptimumCost() {
        dp = new int[numCities + 1][numCities + 1];
        path = new int[numCities + 1][numCities + 1];

        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp.length; j++) {
                if (i == j || j == 0 || i == 0) {
                    dp[i][j] = 0;
                } else if (i > j) {
                    dp[i][j] = Integer.MAX_VALUE;
                } else if (j > i) {
                    String cityName2 = cities.get(j - 1).getName();
                    City city1 = cities.get(i - 1);
                    AdjacentCity adjacentCity = city1.getAdjacent(cityName2);

                    if (adjacentCity != null) {
                        int distance = adjacentCity.getPetrolCost() + adjacentCity.getHotelCost();
                        dp[i][j] = distance;
                    } else {
                        dp[i][j] = Integer.MAX_VALUE;
                    }
                }
            }
        }

        for (int z = 2; z < dp.length; z++) {
            for (int i = 1, j = z; i < dp.length && j < dp.length; i++, j++) {
                if (j > i + 1) {
                    for (int k = i + 1; k < j; k++) {
                        if (dp[i][k] == Integer.MAX_VALUE || dp[k][j] == Integer.MAX_VALUE)
                            continue;
                        else {
                            if (dp[i][k] + dp[k][j] < dp[i][j]) {
                                path[i][j] = k;
                            }
                            dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k][j]);
                        }
                    }
                }
            }
        }
    }

    private void displayResults() {
        List<Route> allPaths = getAllPathsWithCost(startCity, new ArrayList<>());

        //sort the alternative routes based on their costs
        Collections.sort(allPaths, Comparator.comparingInt(Route::getCost));

        StringBuilder sb = new StringBuilder();

        for (Route path : allPaths) {
            sb.append(path.getPath()).append(" (Cost: ").append(path.getCost()).append(")").append("\n");
        }

        alternTableTA.setText(sb.toString());

        dpTableTA.setText(generateTableString(dp));
        pathTableTA.setText(generateTableString(path));
        alternTableTA.setVisible(true);
        costlbl.setText(String.valueOf(dp[1][numCities]));
        costlbl.setVisible(true);
        dpTableTA.setVisible(true);
        pathTableTA.setVisible(true);
        pathlbl.setVisible(true);
        pathl.setVisible(true);
        costl.setVisible(true);
        pathlbl.setText(getPath(path, 1, numCities, cities));
        lbl1.setVisible(true);
        lbl2.setVisible(true);
        lbl3.setVisible(true);
        lbl4.setVisible(true);
        costl.setVisible(true);
        pathl.setVisible(true);

    }

    private String getPath(int[][] path, int start, int end, List<City> cities) {
//        starts with the start city and recursively follows the path until
//        it reaches the end city, concatenating the city names along the way.

        if (start == end) {
            return cities.get(start - 1).getName();
        } else {
            int intermediate = path[start][end];
            if (intermediate == 0 && start != end) {
                return cities.get(start - 1).getName() + " -> " + cities.get(end - 1).getName();
            } else {
                String path1 = getPath(path, start, intermediate, cities);
                String path2 = getPath(path, intermediate, end, cities);
                if (path1.endsWith(cities.get(intermediate - 1).getName())) {
                    // Remove the duplicate city from path1
                    path1 = path1.substring(0, path1.lastIndexOf(cities.get(intermediate - 1).getName()));
                }
                return path1 + path2;
            }
        }
    }

    private String generateTableString(int[][] table) {
        StringBuilder sb = new StringBuilder();

        // Print the city names on the first row
        sb.append("\t\t");
        for (int i = 0; i < numCities; i++) {
            sb.append(cities.get(i).getName()).append("\t");
        }
        sb.append("\n");

        // Print the table
        for (int i = 0; i < table.length; i++) {
            if (i > 0) {
                sb.append(cities.get(i - 1).getName()).append("\t"); // Print the city name on the leftmost column
            }
            for (int j = 0; j < table[i].length; j++) {
                if (table[i][j] == Integer.MAX_VALUE) {
                    sb.append("INF").append("\t");
                } else {
                    sb.append(table[i][j]).append("\t");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    private List<Route> getAllPathsWithCost(String currentCity, List<String> path) {
        path.add(currentCity);
        if (currentCity.equals(endCity)) {
            // Found a path from start to end, calculate its cost
            int cost = calculatePathCost(path);
            return List.of(new Route(String.join(" -> ", path), cost));
//            The current path, along with its cost, is added to a list of Route objects using
//            the String.join() method to concatenate the city names with " -> " as a separator.
//            The list of routes is then returned.
        }

        List<Route> allPaths = new ArrayList<>();

        for (City city : cities) {
            if (city.getName().equals(currentCity)) {
                for (AdjacentCity adjacentCity : city.getAdjacents()) {
                    String adjacentName = adjacentCity.getName();

                    // Avoiding revisiting cities in the current path to prevent cycles
                    if (!path.contains(adjacentName)) {
                        List<Route> paths = getAllPathsWithCost(adjacentName, new ArrayList<>(path));
                        allPaths.addAll(paths);
                    }
                }
                break;
            }
        }

        return allPaths;
    }

    private int calculatePathCost(List<String> path) {
//        iterates over the cities in the path and retrieves the petrol and hotel costs
//        for each adjacent city, summing them up to obtain the total cost

        int cost = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            String cityName = path.get(i);
            String nextCityName = path.get(i + 1);
            for (City city : cities) {
                if (city.getName().equals(cityName)) {
                    AdjacentCity adjacentCity = city.getAdjacent(nextCityName);
                    cost += adjacentCity.getPetrolCost() + adjacentCity.getHotelCost();
                    break;
                }
            }
        }
        return cost;
    }
}
