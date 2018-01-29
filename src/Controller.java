import java.util.*;

public class Controller extends Thread{
    private HashMap<Integer, List<Integer>> macro_tactics = new HashMap<Integer, List<Integer>>();
    private int max_iterations;
    private int current_iteration = 0;

    public Controller(int max_iterations, HashMap<Integer, List<Integer>> macro_tactics){
        this.max_iterations = max_iterations;
        this.macro_tactics = macro_tactics;
    }

    public void run(){
        while (current_iteration < max_iterations){
            try {
                MAPEK mape_k = new MAPEK(macro_tactics);
                mape_k.runMAPEK();
                this.sleep(120 * 1000);
            } catch(InterruptedException e){
                continue;
            }

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
        list1.add(1);
        list1.add(2);
        list1.add(3);
        tactics.put(1, list2);

        new Thread(new Controller(10, tactics)).start();
    }
}