package tacsim;

import java.util.*;


public class MAPEK{
    private HashMap<Integer, List<Double>> macro_tactics;
    private boolean proposed;

    public MAPEK(HashMap<Integer, List<Double>> macro_tactics, boolean proposed){
        this.macro_tactics = macro_tactics;
        this.proposed = proposed;
    }

    public double runMAPEK(int global_symptom){
        if(global_symptom > Controller.MAX_THRESHOLD){
            return getTacticUtility();
        }else{
            return getTacticUtility();
        }
    }

    private double getTacticUtility(){
        HashMap<Integer, Double> utilities = new HashMap<>();

        //for each key in hashmap
        double latency = 0;
        List<Double> latencies = new ArrayList<Double>();

        for(Integer key : macro_tactics.keySet()){
            latencies = macro_tactics.get(key);

            //double mean = getLatencyMean(latencies);
            Random random = new Random();
            int randomInt = random.nextInt(latencies.size()-1);
            latency = (latencies.get(randomInt));
            double sd = getStandardDeviation(latencies);
            double utility = ((14 - latency)*8);

            if(this.proposed){
                utility /= sd;
//                if(utility > 1){utility-=2;}
            }

            utilities.put(key, utility);
        }

        int tactic_index = selectTactic(utilities);
        double run = executeTactic(getLatencyMean(macro_tactics.get(tactic_index)), getStandardDeviation(macro_tactics.get(tactic_index)),latency, latencies);
        macro_tactics.get(tactic_index).add(run);

        return run;
    }

    private double getLatencyMean(List<Double> latencies){
        double mean = 0;

        //calculate mean
        for(int i=0; i< latencies.size(); i++) {
            mean = mean + latencies.get(i);
        }

        return mean / latencies.size();
    }

    private double getStandardDeviation(List<Double> latencies){
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

    private double executeTactic(double mean, double sd, double latency, List<Double> latencies){
        Random random = new Random();
        int randomInt = random.nextInt(latencies.size()-1);
        double latency_time = latencies.get(randomInt);

        if(latency_time > Controller.MAX_THRESHOLD){
            Controller.critical_failure_count++;
        }

        return latency - latency_time;
    }
}