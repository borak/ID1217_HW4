package id1217_hw4.unisexproblem;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class will act as a monitor for the access to the specified Bathroom.
 * It will use condition variables for synchronization in a Signal and Continue 
 * way.
 * @author Kim
 */
public class BathroomMonitor {

    private final Lock lock = new ReentrantLock();
    private final Condition activeMen  = lock.newCondition(); 
    private final Condition activeWomen = lock.newCondition();
    private final Bathroom bathroom;
    private int numberOfActiveWomen = 0;
    private int numberOfActiveMen = 0;

    /**
     * Sets the bathroom object.
     * @param bathroom The bathroom to let the entering people use.
     */
    public BathroomMonitor(Bathroom bathroom) {
        if(bathroom == null) 
            throw new IllegalArgumentException("Can not set a bathroom which is null");
        this.bathroom = bathroom;
    }
    
    /**
     * This method will use the bathroom directly if no women are waiting or,
     * if there are women waiting, wait until they are done.
     */
    public void manEnter() {
        lock.lock();
        try {
          while (numberOfActiveWomen > 0) {
              try {
                  activeWomen.await();
              } catch (InterruptedException ex) {
                  System.err.println("Man interrupted while waiting for bathroom");
              }
          }
          
          numberOfActiveMen++;
          bathroom.use();
        } finally {
          lock.unlock();
        }
    }
    
    /**
     * Notifies that the man is done.
     * If it's the last man, notify all men.
     */
    public void manExit() {
        lock.lock();
        try {
            numberOfActiveMen--;
            if(numberOfActiveMen == 0) 
                activeMen.signalAll();
        } finally {
          lock.unlock();
        }
    }
 
    /**
     * This method will use the bathroom directly if no men are waiting or,
     * if there are men waiting, wait until they are done.
     */
    public void womanEnter() {
       lock.lock();
        try {
          while (numberOfActiveMen > 0) {
              try {
                  activeMen.await();
              } catch (InterruptedException ex) {
                  System.err.println("Woman interrupted while waiting for bathroom");
              }
          }
          
          numberOfActiveWomen++;
          bathroom.use();
        } finally {
          lock.unlock();
        }
    }
    
    /**
     * Notifies that the woman is done.
     * If it's the last woman, notify all men.
     */
    public void womanExit() {
        lock.lock();
        try {
            numberOfActiveWomen--;
            if(numberOfActiveWomen == 0) 
                activeWomen.signalAll();
        } finally {
          lock.unlock();
        }
    }
}
