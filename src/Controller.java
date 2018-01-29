import java.util.*;

public class Controller {
    private HashMap<Integer, List<Integer>> macro_tactic = new HashMap<Integer, List<Integer>>();

    public Controller(){
        Timer timer = new Timer();

        timer.schedule( new TimerTask() {
            public void run() {
                System.out.println("TIME!");
            }
        }, 0, 5*1000);
    }

    public static void main(String[] args){
        Controller run = new Controller();

    }
}