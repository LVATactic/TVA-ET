package tacsim;

import java.lang.reflect.Array;
import java.util.*;

public class Results {
    private Result results;
    private Differences differences;
    private String dataType = "Sample Data";

    public Results(ArrayList<ArrayList<Integer>> results, ArrayList<ArrayList<Double>> differences, boolean usingFile){
        this.results = new Result(results);
        this.differences = new Differences(differences);
        if(usingFile){
            dataType = "Custom Data Set";
        }
    }
}

class Result {
    private ArrayList<Integer> baseline;
    private ArrayList<Integer> proposed;

    public Result(ArrayList<ArrayList<Integer>> list){
        this.baseline = list.get(0);
        this.proposed = list.get(1);
    }
}

class Differences {
    private ArrayList<Double> baseline;
    private ArrayList<Double> proposed;

    public Differences(ArrayList<ArrayList<Double>> list){
        this.baseline = list.get(0);
        this.proposed = list.get(1);
    }
}