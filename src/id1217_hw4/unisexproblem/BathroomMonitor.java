/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id1217_hw4.unisexproblem;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kim
 */
public class BathroomMonitor {

    private final Lock lock = new ReentrantLock();
    private final Condition nonActiveMen  = lock.newCondition(); 
    private final Condition nonActiveWomen = lock.newCondition();

    private final ReentrantReadWriteLock menLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock womenLock = new ReentrantReadWriteLock();
    private final Bathroom bathroom;
    private int activeWomen = 0;
    private int activeMen = 0;

    public BathroomMonitor(Bathroom bathroom) {
        this.bathroom = bathroom;
    }
    
    public void manEnter() {
        womenLock.readLock().lock();
        try {
            while(activeWomen > 0) {
                womenLock.readLock().unlock();
                try {
                    nonActiveWomen.await();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                womenLock.readLock().lock();
            }
        } finally {
            womenLock.readLock().tryLock();
            womenLock.readLock().unlock();
        }
        
        menLock.writeLock().lock();
        try {
            activeMen++;
        } finally {
            menLock.writeLock().unlock();
        }
        bathroom.use();
    }
    
    public void manExit() {
        menLock.writeLock().lock();
        try {
            activeMen--;
        } finally {
            menLock.writeLock().unlock();
        }
        
        menLock.readLock().lock();
        try {
            if(activeMen == 0) {
                nonActiveWomen.signalAll();
            }
        } finally {
            menLock.readLock().unlock();
        }
    }
    
    public void womanEnter() {
        menLock.readLock().lock();
        try {
            while(activeMen > 0) {
                menLock.readLock().unlock();
                try {
                    nonActiveMen.await();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                menLock.readLock().lock();
            }
        } finally {
            menLock.readLock().tryLock();
            menLock.readLock().unlock();
        }
        
        womenLock.writeLock().lock();
        try {
            activeWomen++;
        } finally {
            womenLock.writeLock().unlock();
        }
        bathroom.use();
    }
    
    public void womanExit() {
        womenLock.writeLock().lock();
        try {
            activeWomen--;
        } finally {
            womenLock.writeLock().unlock();
        }
        womenLock.readLock().lock();
        try {
            if(activeWomen == 0) {
                nonActiveMen.signalAll();
            }
        } finally {
            womenLock.readLock().unlock();
        }
    }
}
