
import com.ipl.StrikeRate;

import java.io.*;
import java.util.*;
public class MyPorgram {
    public static void main(String[] args) throws IOException{
     HashSet<Integer>MatchIds=new HashSet<>();
     BufferedReader br=new BufferedReader(new FileReader("data/matches.csv"));
     String line=br.readLine();
     while ((line = br.readLine())!=null){
         String []data=line.split(",");
         int season=Integer.parseInt(data[1]);
         if(season==2017){
             int id=Integer.parseInt(data[0]);
             MatchIds.add(id);
         }
     }
        br.close();

      //reading delivery file
        br=new BufferedReader(new FileReader("data/deliveries.csv"));
        br.readLine();
        HashMap<String,HashMap<String,Integer>>teamRuns=new HashMap<>();
        HashMap<String,HashMap<String,Integer>>teamWickets=new HashMap<>();
        while ((line=br.readLine())!=null){
            String[]data=line.split(",",-1);
            int id=Integer.parseInt(data[0]);
            if(MatchIds.contains(id)){
                String team=data[2];
                String batsmen=data[6];
                int ballRuns=Integer.parseInt(data[15]);
                if(!teamRuns.containsKey(team)){
                    teamRuns.put(team,new HashMap<>());
                }
                HashMap<String,Integer>teamMap=teamRuns.get(team);
                int old=teamMap.getOrDefault(batsmen,0);
                teamMap.put(batsmen,old+ballRuns);
                String bowlingTeam=data[3];
                String bowler=data[8];
                String out=data[19];
                if(out.equals("bowled")||out.equals("caught")||out.equals("lbw")||out.equals("stumped")||out.equals("caught and bowled")||
                    out.equals("hit wicket")){
                      if(!teamWickets.containsKey(bowlingTeam)){
                          teamWickets.put(bowlingTeam,new HashMap<>());

                      }
                    HashMap<String,Integer>wicketMap=teamWickets.get(bowlingTeam);
                    int oldWicket=wicketMap.getOrDefault(bowler,0);
                    wicketMap.put(bowler,oldWicket+1);
                }
            }
        }

        for(String team:teamRuns.keySet()){
            HashMap<String,Integer>runsMap=teamRuns.get(team);
            HashMap<String,Integer>wicketMap=teamWickets.get(team);
            if(wicketMap==null){
                System.out.println(team+" No Allrounder");
                continue;
            }
            String bestPLayer="";
            int bestScore=-1;
            int bestRuns=0;
            int bestWicket=0;
            for(String player:runsMap.keySet()){
                int runs=runsMap.get(player);
                int wickets=wicketMap.getOrDefault(player,0);
                if(runs>=50&&wickets>=5){
                    int score=runs+20*wickets;
                    if(score>bestScore){
                        bestScore=score;
                        bestRuns=runs;
                        bestWicket=wickets;
                        bestPLayer=player;
                    }
                }

            }
            if(bestPLayer.equals("")){
                System.out.println(team+"  No AllRounder");
            }else{
                System.out.println(team+"->"+bestPLayer+" (runs): "+bestRuns+",wicket: "+bestWicket);

            }
        }

    }
}