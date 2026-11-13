
package com.ipl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public static void main(String[] args) throws IOException {

        matchesPerYear();
        matchesWonByTeams();
        extraRunsIn2016();
        economicalBowlers2015();
        topRunScorers();

    }

    // Scenario 1
    public static void matchesPerYear() throws IOException {

        HashMap<Integer, Integer> matchesPerYear = new HashMap<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader("data/matches.csv"))) {

            String line;

            // Skip header
            br.readLine();

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                int year = Integer.parseInt(data[1]);

                int count = matchesPerYear.getOrDefault(year, 0);

                matchesPerYear.put(year, count + 1);
            }
        }

        System.out.println("Matches Per Year");

        TreeMap<Integer, Integer> sortedMatches =
                new TreeMap<>(matchesPerYear);

        for (Integer year : sortedMatches.keySet()) {

            System.out.println(
                    year + " -> " + sortedMatches.get(year)
            );
        }
    }

    // Scenario 2

    // Scenario 2
    public static void matchesWonByTeams() throws IOException {

        HashMap<String, Integer> winsPerTeam = new HashMap<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader("data/matches.csv"))) {

            String line;

            // Skip header
            br.readLine();

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                String winner = data[10];

                if (!winner.isEmpty()) {

                    int count = winsPerTeam.getOrDefault(winner, 0);

                    winsPerTeam.put(winner, count + 1);
                }
            }
        }

        System.out.println("\nMatches Won By Each Team");

        TreeMap<String, Integer> sortedWins =
                new TreeMap<>(winsPerTeam);

        for (String team : sortedWins.keySet()) {

            System.out.println(
                    team + " -> " + sortedWins.get(team)
            );
        }
    }

    public static void extraRunsIn2016() throws IOException {
        HashSet<Integer> matchIds2016 = new HashSet<>();

        HashMap<String, Integer> extraRunsPerTeam = new HashMap<>();

        // Step 1: Get match IDs of 2016
        try (BufferedReader br = new BufferedReader(
                new FileReader("data/matches.csv"))) {

            String line;

            br.readLine();

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                int matchId = Integer.parseInt(data[0]);
                int year = Integer.parseInt(data[1]);

                if (year == 2016) {
                    matchIds2016.add(matchId);
                }
            }
        }

        // Step 2: Read deliveries.csv
        try (BufferedReader br = new BufferedReader(
                new FileReader("data/deliveries.csv"))) {

            String line;

            br.readLine();

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                int matchId = Integer.parseInt(data[0]);

                if (matchIds2016.contains(matchId)) {

                    String bowlingTeam = data[3];

                    int extraRuns = Integer.parseInt(data[16]);

                    extraRunsPerTeam.put(
                            bowlingTeam,
                            extraRunsPerTeam.getOrDefault(bowlingTeam, 0) + extraRuns
                    );
                }
            }
        }

        // Step 3: Print result
        System.out.println("\nExtra Runs Conceded in 2016");

        for (String team : extraRunsPerTeam.keySet()) {

            System.out.println(team + " -> " + extraRunsPerTeam.get(team));
        }
    }

    //4th ques Most economical Bowler
    public static void economicalBowlers2015() throws IOException {

        HashSet<Integer> matchIds2015 = new HashSet<>();

        HashMap<String, Integer> runsConceded = new HashMap<>();
        HashMap<String, Integer> ballsBowled = new HashMap<>();

        // Step 1: Collect 2015 match IDs
        try (BufferedReader br = new BufferedReader(
                new FileReader("data/matches.csv"))) {

            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                int matchId = Integer.parseInt(data[0]);
                int year = Integer.parseInt(data[1]);

                if (year == 2015) {
                    matchIds2015.add(matchId);
                }
            }
        }

        // Step 2: Read deliveries and calculate bowling statistics
        try (BufferedReader br = new BufferedReader(
                new FileReader("data/deliveries.csv"))) {

            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                int matchId = Integer.parseInt(data[0]);

                if (matchIds2015.contains(matchId)) {

                    String bowler = data[8];

                    int totalRuns = Integer.parseInt(data[17]);
                    int byeRuns = Integer.parseInt(data[11]);
                    int legbyeRuns = Integer.parseInt(data[12]);
                    int penaltyRuns = Integer.parseInt(data[14]);

                    int wideRuns = Integer.parseInt(data[10]);
                    int noBallRuns = Integer.parseInt(data[13]);

                    // Runs conceded by the bowler
                    int conceded = totalRuns - byeRuns - legbyeRuns - penaltyRuns;

                    runsConceded.put(
                            bowler,
                            runsConceded.getOrDefault(bowler, 0) + conceded
                    );

                    // Count only legal deliveries
                    if (wideRuns == 0 && noBallRuns == 0) {

                        ballsBowled.put(
                                bowler,
                                ballsBowled.getOrDefault(bowler, 0) + 1
                        );
                    }
                }
            }
        }

        // Step 3: Calculate economy and sort bowlers
        TreeMap<Double, ArrayList<String>> economicalBowlers = new TreeMap<>();

        for (String bowler : runsConceded.keySet()) {

            int runs = runsConceded.get(bowler);
            int balls = ballsBowled.getOrDefault(bowler, 0);

            if (balls > 0) {

                double economy = (runs * 6.0) / balls;

                economicalBowlers
                        .computeIfAbsent(economy, k -> new ArrayList<>())
                        .add(bowler);
            }
        }

        // Step 4: Print top 10 economical bowlers
        System.out.println("\nTop 10 Economical Bowlers in 2015");

        int count = 0;

        for (Double economy : economicalBowlers.keySet()) {

            for (String bowler : economicalBowlers.get(economy)) {

                System.out.printf("%s -> %.2f%n", bowler, economy);

                count++;

                if (count == 10) {
                    return;
                }
            }
        }
    }
    public static void topRunScorers() throws IOException {

        HashMap<String, Integer> batsmenRuns = new HashMap<>();

        // Step 1: Read deliveries.csv
        try (BufferedReader br = new BufferedReader(
                new FileReader("data/deliveries.csv"))) {

            String line;

            // Skip header
            br.readLine();

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                String batsman = data[6];
                int runs = Integer.parseInt(data[15]);

                // Add runs batsman-wise
                batsmenRuns.put(
                        batsman,
                        batsmenRuns.getOrDefault(batsman, 0) + runs
                );
            }
        }

        // Step 2: Sort batsmen by total runs (descending)
        TreeMap<Integer, ArrayList<String>> sortedBatsmen = new TreeMap<>(
                Collections.reverseOrder()
        );

        for (String batsman : batsmenRuns.keySet()) {

            int runs = batsmenRuns.get(batsman);

            sortedBatsmen
                    .computeIfAbsent(runs, k -> new ArrayList<>())
                    .add(batsman);
        }

        // Step 3: Print top 10 batsmen
        System.out.println("\nTop 10 Run Scorers in IPL");

        int count = 0;

        for (Integer runs : sortedBatsmen.keySet()) {

            for (String batsman : sortedBatsmen.get(runs)) {

                System.out.println(batsman + " -> " + runs);

                count++;

                if (count == 10) {
                    return;
                }
            }
        }
    }

}



