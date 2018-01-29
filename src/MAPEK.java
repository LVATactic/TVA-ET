import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MAPEK{
    private HashMap<Integer, List<Integer>> macro_tactics;

    public MAPEK(HashMap<Integer, List<Integer>> macro_tactics){
        this.macro_tactics = macro_tactics;
    }

    public void runMAPEK(){
        HashMap<Integer, Double> utilities = getTacticUtility();
        System.out.println(utilities);
    }

    private HashMap<Integer, Double> getTacticUtility(){
        HashMap<Integer, Double> utilities = new HashMap<>();

        //for each key in hashmap
        for(Integer key : macro_tactics.keySet()){
            List<Integer> latencies = macro_tactics.get(key);

            double mean = getLatencyMean(latencies);
            double sd = getStandardDeviation(latencies);
            double utility = ((14 - mean)*8)/sd;

            utilities.put(key, utility);
        }

        selectTactic(utilities);
        return utilities;
    }

    private double getLatencyMean(List<Integer> latencies){
        double mean = 0;

        //calculate mean
        for(int i=0; i< latencies.size(); i++) {
            mean = mean + latencies.get(i);
        }

        return mean / latencies.size();
    }

    private double getStandardDeviation(List<Integer> latencies){
        double sum = 0;
        double mean = getLatencyMean(latencies);

        //calculate summation
        for (int i = 0; i < latencies.size(); i++) {
            sum += Math.pow(latencies.get(i) - mean, 2);
        }

        return Math.sqrt(sum/(latencies.size()));
    }

    private int selectTactic(HashMap<Integer, Double> utilities){
        int highest_key = 0;
        double highest_value = 0;
        for(int key : utilities.keySet()){
            if (utilities.get(key) > highest_value){
                highest_key = key;
                highest_value = utilities.get(key);
            }
        }

        return highest_key;
    }
}