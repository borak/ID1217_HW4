package id1217_hw4.unisexproblem;

import java.util.Random;

/**
 *
 * @author Kim
 */
public class WorkerThread extends Thread implements Runnable {

    public enum Gender {
        MALE, FEMALE;
    }
    private final BathroomMonitor bathroom;
    private final int id;
    private final Gender gender;
    private final static int WORK_TIME = 30 * 1000;
    
    public WorkerThread(int id, Gender gender, BathroomMonitor bathroom) {
        super();
        this.bathroom = bathroom;
        this.id = id;
        this.gender = gender;
    }
    
    @Override
    public void run() {
        Random rand = new Random();
        
        while(true) {
            long workTime = rand.nextInt(WORK_TIME);

            try {
                this.sleep(workTime);
                if(gender == Gender.MALE) {
                    bathroom.manEnter();
                    bathroom.manExit();
                } else {
                    bathroom.womanEnter();
                    bathroom.womanExit();
                }
            } catch (InterruptedException ex) {
                System.err.println(getGender() + "[" + getId() + ": " + ex.getMessage());
            }
        }
    }
    
    @Override
    public long getId() {
        return id;
    }
    
    public Gender getGender() {
        return gender;
    }
}
