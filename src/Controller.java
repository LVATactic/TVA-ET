import java.util.*;

public class Controller{
    private HashMap<Integer, List<Integer>> macro_tactics = new HashMap<Integer, List<Integer>>();
    private int max_iterations;
    private int current_iteration = 0;

    public Controller(int max_iterations, HashMap<Integer, List<Integer>> macro_tactics){
        this.max_iterations = max_iterations;
        this.macro_tactics = macro_tactics;

        while (current_iteration < max_iterations){
            MAPEK mape_k = new MAPEK(macro_tactics);
            mape_k.runMAPEK();

            current_iteration++;
        }
    }
    
    public static void main(String[] args){
        HashMap<Integer, List<Integer>> tactics = new HashMap<>();

        ArrayList<Integer> list1 = new ArrayList<>();
        list1.add(5);
        list1.add(10);
        list1.add(15);
        tactics.put(0, list1);

        ArrayList<Integer> list2 = new ArrayList<>();
        list2.add(1);
        list2.add(2);
        list2.add(3);
        tactics.put(1, list2);

        ArrayList<Integer> list3 = new ArrayList<>();
        list3.add(0);
        list3.add(30);
        list3.add(60);
        list3.add(90);
        list3.add(120);
        tactics.put(2, list3);

        new Controller(10, tactics);
    }
}