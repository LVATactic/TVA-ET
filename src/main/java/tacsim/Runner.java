package tacsim;

import java.util.*;

public class Runner {
    public static void main(String[] args){
        System.out.println("Simulation starting.....");


        new Controller(100, SampleData.getTactics(), new HashMap<>());
        System.out.println("Simulation over....");
    }
}
