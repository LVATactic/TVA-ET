import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.lang.Math;

public class MAPEK{
    public MAPEK(){
        
    }

    public List<Double> getTacticUtility(HashMap<Integer, List<Integer>> map){
        List<Double> utilities = new ArrayList<>();

        //for each key in hashmap
        for(Integer key : map.keySet()){
            List<Integer> latencies = map.get(key);

            double mean = getLatencyMean(latencies);
            double sd = getStandardDeviation(latencies);
            double utility = ((14 - mean)*8)/sd;

            utilities.add(utility);
        }

        return utilities;
    }

    public double getLatencyMean(List<Integer> latencies){
        double mean = 0;

        //calculate mean
        for(int i=0; i< latencies.size(); i++) {
            mean = mean + latencies.get(i);
        }
        return mean / latencies.size();
    }

    public double getStandardDeviation(List<Integer> latencies){
        double sum = 0;
        double mean = getLatencyMean(latencies);

        //calculate summation
        for (int i = 0; i < latencies.size(); i++) {
            sum += Math.pow(latencies.get(i) - mean, 2);
        }

        return Math.sqrt(sum/(latencies.size()-1));
    }
}