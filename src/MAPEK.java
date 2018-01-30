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
        int latency = 0;
        for(Integer key : macro_tactics.keySet()){
            List<Integer> latencies = macro_tactics.get(key);

            //double mean = getLatencyMean(latencies);
            Random random = new Random();
            int randomInt = random.nextInt(latencies.size()-1);
            latency = (latencies.get(randomInt));
            double sd = getStandardDeviation(latencies);
            double utility = ((14 - latency)*8);

            if(this.proposed){
                utility /= sd;
            }

            utilities.put(key, utility);
        }

        int tactic_index = selectTactic(utilities);
        executeTactic(getLatencyMean(macro_tactics.get(tactic_index)), getStandardDeviation(macro_tactics.get(tactic_index)),latency);
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

    private void executeTactic(double mean, double sd,int latency){
        Random r = new Random();
        double latency_time = r.nextGaussian()*sd + mean;
        System.out.printf("%d - %.2f = %.2f\n",latency,latency_time,(latency - latency_time));

        if(latency_time > Controller.MAX_THRESHOLD){
            Controller.critical_failure_count++;
        }
    }
}