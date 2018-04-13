package tacsim;

public class test {

    public static void main(String[] args) {
        test t = new test();
        t.run();

    }

    private void run(){
        System.out.println(Round(3.1234534242,2));
    }

    public  double Round(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

}