import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MAPEK{
    private HashMap<Integer, List<Integer>> macro_tactics;

    public MAPEK(HashMap<Integer, List<Integer>> macro_tactics){
        this.macro_tactics = macro_tactics;
    }

    public void runMAPEK(){
        List<Double> utilities = getTacticUtility();
        System.out.println(utilities);
    }

    public List<Double> getTacticUtility(){
        List<Double> utilities = new ArrayList<>();

        //for each key in hashmap
        for(Integer key : macro_tactics.keySet()){
            List<Integer> latencies = macro_tactics.get(key);

            double mean = getLatencyMean(latencies);
            double sd = getStandardDeviation(latencies);
            double utility = ((14 - mean)*8)/sd;

            utilities.add(utility);
        }

        selectTactic(utilities);
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

        return Math.sqrt(sum/(latencies.size()));
    }

    public int selectTactic(List<Double> utilities){
        double max_utility =  utilities.get(utilities.size()-1);

        return 2;
    }
}