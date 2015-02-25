package id1217_hw4;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kim
 */
public class VehicleRepairStationMonitor {
    
    private static final int REPAIR_MAX_TIME = 10*1000;
    
    public void repair(VehicleThread v) {
        Random rand = new Random();
        int time = rand.nextInt(REPAIR_MAX_TIME);

        //System.out.println(new Timestamp(new Date().getTime()) + ": "  +
        //        wt.getGender() + "[" + wt.getId() + "] uses the bathroom.");
        try {
            v.sleep(time);
        } catch (InterruptedException ex) {
            //System.err.println(wt.getGender() + "[" + wt.getId() + ": " + ex.getMessage());
        }
    }
}
