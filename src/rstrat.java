
import com.ipl.StrikeRate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.*;

public class rstrat {
    public static void main(String[] args) throws IOException {
        HashSet<Integer>matchIds=new HashSet<>();
        BufferedReader br=new BufferedReader(new FileReader("data/matches.csv"));
        String line;
        br.readLine();
        while ((line=br.readLine())!=null){
            String[] data=line.split(",");
            int season=Integer.parseInt(data[1]);
            if(season==2015){
                int id=Integer.parseInt(data[0]);
                matchIds.add(id);
            }
        }
        br.close();
        HashMap<String,HashMap<String,Integer>>teamRuns=new HashMap<>();
        br=new BufferedReader(new FileReader("data/deliveries.csv"));
        HashMap<String,HashMap<String,Integer>>teamBalls=new HashMap<>();
        br.readLine();
        while ((line=br.readLine())!=null){
            String[] data=line.split(",");
            int id=Integer.parseInt(data[0]);
            if(matchIds.contains(id)){
              String battingTeam=data[2];
              String batsman=data[6];
              int ballRuns=Integer.parseInt(data[16]);
              if(!teamRuns.containsKey(battingTeam)){
                  teamRuns.put(battingTeam,new HashMap<>());
              }
              int old=teamRuns.get(battingTeam).getOrDefault(batsman,0);
              teamRuns.get(battingTeam).put(batsman,old+ballRuns);
              int wide=Integer.parseInt(data[10]);
              if(wide==0){
                if(!teamBalls.containsKey(battingTeam)){
                    teamBalls.put(battingTeam,new HashMap<>());
                }
                int oldBalls=teamBalls.get(battingTeam).getOrDefault(batsman,0);
                teamBalls.get(battingTeam).put(batsman,oldBalls+1);
              }

            }
        }


        for(String team:teamRuns.keySet()){
            HashMap<String,Integer>BatsmanRuns=teamRuns.get(team);
            HashMap<String,Integer>BatsmanBalls=teamBalls.get(team);
            double bestStrikeRate=0;
            String bestBatsman="";
            for(String batsman:BatsmanRuns.keySet()){
                int runsBats=BatsmanRuns.get(batsman);
               int ballsBats=BatsmanBalls.get(batsman);
               double strikeRate=(runsBats*100.0)/ballsBats;
               if(strikeRate>bestStrikeRate){
                bestStrikeRate=strikeRate;
                bestBatsman=batsman;
                }
            }
            System.out.println(" Best batsman "+bestBatsman+" best strike rate: "+bestStrikeRate+" Team Name: "+team);

        }






    }
}