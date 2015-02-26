/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id1217_hw4.unisexproblem;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Kim
 */
public class BathroomMonitor {

    private final Lock lock = new ReentrantLock();
    private final Condition nonActiveMen  = lock.newCondition(); 
    private final Condition nonActiveWomen = lock.newCondition();

    private final Lock menLock = new ReentrantLock();
    private final Lock womenLock = new ReentrantLock();
    private final Bathroom bathroom;
    private int activeWomen = 0;
    private int activeMen = 0;

    public BathroomMonitor(Bathroom bathroom) {
        this.bathroom = bathroom;
    }
    
    public void manEnter() {

        while(activeWomen > 0) {
            nonActiveWomen.awaitUninterruptibly();
        }
        
        bathroom.use();
    }
    
    public void manExit() {
        //signal if last
    }
    
    public void WomanEnter() {
        
    }
    
    public void WomanExit() {
        
    }
}
