package com.ipl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Inet4Address;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;

public class StrikeRate {
    public static void main(String[] args){


            // Step 1: Store 2015 match IDs
            HashSet<Integer> matchIds2015 = new HashSet<>();

            try (BufferedReader br = new BufferedReader(
                    new FileReader("data/matches.csv"))) {

                String line;
                br.readLine(); // Skip header

                while ((line = br.readLine()) != null) {

                    String[] data = line.split(",");

                    int matchId = Integer.parseInt(data[0]);
                    int year = Integer.parseInt(data[1]);

                    if (year == 2015) {
                        matchIds2015.add(matchId);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            // Step 2: Create nested HashMaps
            HashMap<String, HashMap<String, Integer>> teamRuns = new HashMap<>();

            HashMap<String, HashMap<String, Integer>> teamBalls = new HashMap<>();


            // Step 3: Read deliveries.csv
            try (BufferedReader br = new BufferedReader(
                    new FileReader("data/deliveries.csv"))) {

                String line;
                br.readLine(); // Skip header

                while ((line = br.readLine()) != null) {

                    String[] data = line.split(",");

                    int matchId = Integer.parseInt(data[0]);

                    // Process only 2015 matches
                    if (matchIds2015.contains(matchId)) {

                        String team = data[2];
                        String batsman = data[6];

                        int runs = Integer.parseInt(data[15]);
                        int wideRuns = Integer.parseInt(data[10]);


                        // Check whether team exists
                        if (!teamRuns.containsKey(team)) {

                            teamRuns.put(team, new HashMap<>());
                            teamBalls.put(team, new HashMap<>());
                        }


                        // Get inner HashMaps
                        HashMap<String, Integer> batsmenRuns = teamRuns.get(team);

                        HashMap<String, Integer> batsmenBalls = teamBalls.get(team);


                        // Update batsman's runs
                        int oldRuns = batsmenRuns.getOrDefault(batsman, 0);

                        batsmenRuns.put(batsman, oldRuns + runs);


                        // Update balls faced
                        if (wideRuns == 0) {

                            int oldBalls = batsmenBalls.getOrDefault(batsman, 0);

                            batsmenBalls.put(batsman, oldBalls + 1);
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            // Step 4: Find best strike-rate batsman of each team
            for (String team : teamRuns.keySet()) {

                HashMap<String, Integer> batsmenRuns = teamRuns.get(team);

                HashMap<String, Integer> batsmenBalls = teamBalls.get(team);

                String bestBatsman = "";
                double maxStrikeRate = -1;


                // Check every batsman of current team
                for (String batsman : batsmenRuns.keySet()) {

                    int runs = batsmenRuns.get(batsman);

                    int balls = batsmenBalls.getOrDefault(batsman, 0);


                    if (balls > 0) {

                        double strikeRate = (runs * 100.0) / balls;


                        // Check maximum strike rate
                        if (strikeRate > maxStrikeRate) {

                            maxStrikeRate = strikeRate;
                            bestBatsman = batsman;
                        }
                    }
                }


                // Print result
                if (!bestBatsman.isEmpty()) {

                    System.out.println(
                            team + " -> " +
                                    bestBatsman + " -> " +
                                    String.format("%.2f", maxStrikeRate)
                    );
                }
            }


    }
}