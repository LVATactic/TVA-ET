import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class MAPEK{
    private HashMap<Integer, List<Integer>> macro_tactics;
    private boolean proposed;

    public MAPEK(HashMap<Integer, List<Integer>> macro_tactics, boolean proposed){
        this.macro_tactics = macro_tactics;
        this.proposed = proposed;
    }

    public void runMAPEK(int global_symptom){
        if(global_symptom > 5){
            getTacticUtility();
        }
    }

    private HashMap<Integer, Double> getTacticUtility(){
        HashMap<Integer, Double> utilities = new HashMap<>();

        //for each key in hashmap
        for(Integer key : macro_tactics.keySet()){
            List<Integer> latencies = macro_tactics.get(key);

            double mean = getLatencyMean(latencies);
            double sd = getStandardDeviation(latencies);
            double utility = ((14 - mean)*8);

            if(this.proposed){
                utility /= sd;
            }

            utilities.put(key, utility);
        }

        int tactic_index = selectTactic(utilities);
        executeTactic(getLatencyMean(macro_tactics.get(tactic_index)), getStandardDeviation(macro_tactics.get(tactic_index)));
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

    private void executeTactic(double mean, double sd){
        Random r = new Random();
        double latency_time = r.nextGaussian()*sd + mean;

        if(latency_time > Controller.MAX_THRESHOLD){
            Controller.kill_count++;
        }
    }
}