package tacsim;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Controller{
    private HashMap<Integer, List<Integer>> macro_tactics = new HashMap<Integer, List<Integer>>();
    private int max_iterations;
    private int cycle = 0;
    private final int GLOBAL_SYMPTOM = 10;

    static final private int MAX_CYCLES = 50;
    static final public int MAX_THRESHOLD = 5;
    static public int critical_failure_count = 0;

    private ArrayList<ArrayList<Double>> differences;


    public Controller(int max_iterations, HashMap<Integer, List<Integer>> macro_tactics){
        this.max_iterations = max_iterations;
        this.macro_tactics = macro_tactics;

        ArrayList<ArrayList<Integer>> combined = new ArrayList<>();

        combined.add(runInstance(false)); // Baseline
        combined.add(runInstance(true)); // Proposed algorithm

        writeToCSV(combined, differences);
    }

    private ArrayList<Integer> runInstance(boolean proposed){
        ArrayList<Integer> critical_failures_list = new ArrayList<>();
        this.differences = new ArrayList<>();
        int current_iteration = 0;

        while(cycle < MAX_CYCLES) {
            critical_failure_count = 0;
            current_iteration = 0;

            ArrayList<Double> list_of_differences = new ArrayList<>();

            double latency_difference = 0;
            while (current_iteration < max_iterations) {
                MAPEK mape_k = new MAPEK(macro_tactics, proposed);
                latency_difference = mape_k.runMAPEK(GLOBAL_SYMPTOM);
                list_of_differences.add(latency_difference);

                current_iteration++;
            }
            cycle++;
            critical_failures_list.add(Controller.critical_failure_count);
            differences.add(list_of_differences);
        }

        cycle = 0;
        return critical_failures_list;
    }

    private void writeToCSV(ArrayList<ArrayList<Integer>> lists, ArrayList<ArrayList<Double>> differences){
        String csv_buffer = "Baseline (Critical Failures),TacSim (Critical Failures)\n";
        try{
            FileWriter writer = new FileWriter("output/data.csv");

            ArrayList<Integer> baseline = lists.get(0);
            ArrayList<Integer> proposed = lists.get(1);

            for(int i = 0; i < baseline.size(); i++){
                String line = baseline.get(i) + "," + proposed.get(i) + "\n";
                csv_buffer += line;
            }

            writer.write(csv_buffer);
            writer.close();


            
            FileWriter diff_writer = new FileWriter("output/latency_diffs.csv");
            String csv_diff_buffer = "Baseline (Latency Diff),TacSim (Latency Diff)\n";

            ArrayList<Double> baseline_diffs = differences.get(0);
            ArrayList<Double> proposed_diffs = differences.get(1);

            for(int i = 0; i < baseline_diffs.size(); i++){
                String line = baseline_diffs.get(i) + "," + proposed_diffs.get(i) + "\n";
                csv_diff_buffer += line;
            }

            diff_writer.write(csv_diff_buffer);
            diff_writer.close();

        } catch(IOException e){
            System.err.println("Unable to write CSV file. Please check directory/file permissions.");
        }
    }
}