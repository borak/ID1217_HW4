package id1217_hw4.unisexproblem;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

/**
 * This class represents a worker simulator. 
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
    
    /**
     * Initializes the state of the worker.
     * @param id The thread ID.
     * @param gender The gender which the worker are.
     * @param bathroom The bathroom to use when it's time.
     */
    public WorkerThread(int id, Gender gender, BathroomMonitor bathroom) {
        super();
        this.bathroom = bathroom;
        this.id = id;
        this.gender = gender;
    }
    
    /**
     * Simulates the bahviour of a worker by letting it work for a random 
     * amount of time and then go to the bathroom occasionally.
     */
    @Override
    public void run() {
        Random rand = new Random();
        
        while(true) {
            long workTime = rand.nextInt(WORK_TIME);

            try {
                this.sleep(workTime);
                
                System.out.println(new Timestamp(new Date().getTime()) 
                        + ": "  + getGender() + "[" + getId() 
                        + "] queued for the Bathroom.");
                
                if(gender == Gender.MALE) {
                    bathroom.manEnter();
                } else {
                    bathroom.womanEnter();
                }
                System.out.println(new Timestamp(new Date().getTime()) 
                        + ": "  + getGender() + "[" + getId() 
                        + "] entered the Bathroom.");
                    

               if(gender == Gender.MALE) {
                    bathroom.manExit();
                } else {
                    bathroom.womanExit();
                }
                System.out.println(new Timestamp(new Date().getTime()) 
                        + ": "  + getGender() + "[" + getId() 
                        + "] exited the Bathroom.");
                
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
