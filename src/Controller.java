import java.util.*;

public class Controller extends Thread{
    private HashMap<Integer, List<Integer>> macro_tactic = new HashMap<Integer, List<Integer>>();
    private int max_iterations;
    private int current_iteration = 0;

    public Controller(int max_iterations){
        this.max_iterations = max_iterations;
    }

    public void run(){
        while (current_iteration < max_iterations){
            try {
                System.out.println("HELLO FROM " + current_iteration);
                this.sleep(120 * 1000);
            } catch(InterruptedException e){
                continue;
            }

            current_iteration++;
        }
    }

    public static void main(String[] args){
        new Thread(new Controller(10)).start();
    }
}