package id1217_hw4.unisexproblem;

import java.util.Random;

/**
 * Developed for course ID1217 Parallel Programming, KTH
 * 
 * @author Kim
 */
public class Bathroom {

    private static final int USE_TIME_DEFAULT = 10* 1000;
    private final int USE_TIME;
    
    public Bathroom() {
        this(USE_TIME_DEFAULT);
    }
    
    public Bathroom(int useTime) {
        USE_TIME = useTime;
    }

    /**
     * Simulates a bathroom usage by letting the specified thread sleep for 
     * a random amount of time.
     */
    public void use() {
        Random rand = new Random();
        int time = rand.nextInt(USE_TIME);

        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            System.err.println("Bathroom usage has been interrupted.");
        }
    }


}
