import java.util.List;

import java.lang.Math;

public class MAPEK{
    public MAPEK(){
        
    }

    public double getStandardDeviation(List<Integer> latencies){
        double sd = 0;
        double mean = 0;

        //calculate mean
        for(int i=0; i< latencies.size(); i++) {
            mean = mean + latencies.get(i);
        }
        mean = mean / latencies.size();

        //calculate summation
        for (int i = 0; i < latencies.size(); i++) {
            sd += Math.pow(latencies.get(i) - mean, 2);
        }

        return Math.sqrt(sd/(latencies.size()-1));
    }
}