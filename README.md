# Distance Between Cities (Dynamic Programming)

This repository contains a **Dynamic Programming (DP)** solution to calculate the shortest or most efficient distance between cities. This project demonstrates my understanding of dynamic programming, optimization techniques, and problem-solving skills. It was developed as part of my academic and personal learning journey.

## Table of Contents

1. [Project Overview](#project-overview)
2. [Features](#features)
3. [Challenges and Solutions](#challenges-and-solutions)
4. [Installation](#installation)
5. [Usage](#usage)
6. [License](#license)

---

## Project Overview

The problem of finding the shortest or most efficient distance between cities is a classic example of optimization. This project uses **Dynamic Programming** to solve this problem efficiently. The implementation:

- Represents cities and distances as a graph.
- Uses a DP approach to avoid redundant calculations and improve performance.
- Outputs the optimal distance between two cities.

This project is written in **Java** and is designed to be clear, modular, and easy to extend.

---

## Features

- **Graph Representation**: Cities and distances are represented as a graph (e.g., adjacency matrix or list).
- **Dynamic Programming Solution**: Implements a DP table to store intermediate results and avoid recalculating distances.
- **Efficient and Scalable**: Handles larger datasets efficiently due to the optimized DP approach.
- **Customizable**: Can be extended to include additional constraints, such as traffic or road conditions.

---

## Challenges and Solutions

### Challenge: Figuring Out the DP Relation and Table Design
- **Problem**:  
  When I first started implementing the DP solution, I struggled to define the correct **DP relation** and design the **DP table**. I knew I needed to store intermediate results to avoid redundant calculations, but I wasn’t sure how to structure the table or what the recurrence relation should look like. This made it difficult to translate the problem into a working DP solution.

- **Solution**:  
  To overcome this challenge, I took the following steps:
  1. **Broke Down the Problem**: I started by analyzing smaller subproblems. For example, I asked myself, "What is the shortest distance from City A to City B if I only consider one intermediate city?"
  2. **Identified the Recurrence Relation**: After analyzing smaller cases, I realized that the shortest distance to a city could be expressed in terms of the shortest distances to its neighboring cities. This led me to the recurrence relation:
     ```
     dp[i][j] = min(dp[i][j], dp[i][k] + dp[k][j])
     ```
     where `dp[i][j]` represents the shortest distance from city `i` to city `j`, and `k` is an intermediate city.
  3. **Designed the DP Table**: I created a 2D DP table where each cell `dp[i][j]` stores the shortest distance from city `i` to city `j`. I initialized the table with the direct distances between cities and then iteratively updated it using the recurrence relation.
  4. **Validated the Solution**: I tested the DP solution with various graphs to ensure it worked correctly and handled edge cases.

This process helped me not only solve the problem but also deepen my understanding of how to approach DP problems in general.

---

## Installation

To run this project locally, follow these steps:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/taladabbagh/Distance-Between-Cities-DP.git
   ```

2. **Navigate to the project directory**:
   ```bash
   cd Distance-Between-Cities-DP
   ```

3. **Compile and Run the Program**:
   - Compile the main Java file:
     ```bash
     javac Main.java
     ```
   - Run the program:
     ```bash
     java Main
     ```

---

## Usage

1. **Input the Graph**:
   - The program accepts a graph as input. You can either:
     - Enter the graph manually when prompted (e.g., adjacency matrix or list).
     - Provide a file containing the graph data.

2. **Run the Algorithm**:
   - The program will compute the shortest or most efficient distance between two specified cities using the DP approach.

3. **View the Output**:
   - The program will display the optimal distance and the path (if applicable).

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
