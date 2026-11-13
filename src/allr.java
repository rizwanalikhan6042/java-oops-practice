import java.io.*;
import java.util.*;

public class allr {

    static int YEAR = 2017;
    static int MIN_RUNS = 50;      // all-rounder ke kam se kam itne runs
    static int MIN_WICKETS = 5;    // aur kam se kam itne wickets

    // Step 1: us saal ke match id nikalo
    static HashSet<Integer> getMatchIds() throws IOException {
        HashSet<Integer> ids = new HashSet<>();
        BufferedReader br = new BufferedReader(new FileReader("data/matches.csv"));
        br.readLine(); // header skip

        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            int matchId = Integer.parseInt(data[0]);
            int year = Integer.parseInt(data[1]);
            if (year == YEAR) {
                ids.add(matchId);
            }
        }
        br.close();
        return ids;
    }

    // Map me value jodne ka chhota function: team -> (player -> number)
    static void addToMap(HashMap<String, HashMap<String, Integer>> map,
                         String team, String player, int value) {
        if (!map.containsKey(team)) {
            map.put(team, new HashMap<>());
        }
        HashMap<String, Integer> inner = map.get(team);
        int old = inner.getOrDefault(player, 0);
        inner.put(player, old + value);
    }

    // Step 2: deliveries padho, runs aur wickets jodo
    static void readDeliveries(HashSet<Integer> ids,
                               HashMap<String, HashMap<String, Integer>> teamRuns,
                               HashMap<String, HashMap<String, Integer>> teamWickets) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("data/deliveries.csv"));
        br.readLine(); // header skip

        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",", -1); // -1: khaali columns bhi rakho

            int matchId = Integer.parseInt(data[0]);
            int isSuperOver = Integer.parseInt(data[9]);

            // sirf us saal ki balls, super over nahi
            if (!ids.contains(matchId) || isSuperOver != 0) {
                continue;
            }

            String battingTeam = data[2];
            String bowlingTeam = data[3];
            String batsman = data[6];
            String bowler = data[8];
            int runs = Integer.parseInt(data[15]);
            String dismissal = data[19];

            // batsman ke runs jodo
            addToMap(teamRuns, battingTeam, batsman, runs);

            // bowler ka wicket (run out wagairah bowler ke naam nahi jaate)
            if (dismissal.equals("bowled") || dismissal.equals("caught")
                    || dismissal.equals("lbw") || dismissal.equals("stumped")
                    || dismissal.equals("caught and bowled") || dismissal.equals("hit wicket")) {
                addToMap(teamWickets, bowlingTeam, bowler, 1);
            }
        }
        br.close();
    }

    // Step 3: har team ka best all-rounder dhundo
    static void printBestAllRounders(HashMap<String, HashMap<String, Integer>> teamRuns,
                                     HashMap<String, HashMap<String, Integer>> teamWickets) {
        for (String team : teamRuns.keySet()) {

            HashMap<String, Integer> runsMap = teamRuns.get(team);
            HashMap<String, Integer> wicketsMap = teamWickets.get(team);

            String bestPlayer = "";
            int bestScore = -1;
            int bestRuns = 0;
            int bestWickets = 0;

            if (wicketsMap != null) {
                for (String player : runsMap.keySet()) {
                    int runs = runsMap.get(player);
                    int wickets = wicketsMap.getOrDefault(player, 0);

                    // dono me kaam kiya ho tabhi all-rounder
                    if (runs >= MIN_RUNS && wickets >= MIN_WICKETS) {
                        int score = runs + 20 * wickets;
                        if (score > bestScore) {
                            bestScore = score;
                            bestPlayer = player;
                            bestRuns = runs;
                            bestWickets = wickets;
                        }
                    }
                }
            }

            if (bestPlayer.equals("")) {
                System.out.println(team + " -> koi all-rounder nahi");
            } else {
                System.out.println(team + " -> " + bestPlayer
                        + " (runs: " + bestRuns + ", wickets: " + bestWickets + ")");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        HashSet<Integer> ids = getMatchIds();

        HashMap<String, HashMap<String, Integer>> teamRuns = new HashMap<>();
        HashMap<String, HashMap<String, Integer>> teamWickets = new HashMap<>();

        readDeliveries(ids, teamRuns, teamWickets);
        printBestAllRounders(teamRuns, teamWickets);
    }
}