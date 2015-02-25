package id1217_hw4;

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
public class Startup {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        VehicleRepairStationMonitor station = new VehicleRepairStationMonitor();
        Random random = new Random();
        
        for(int i=0; i<2; i++) {
            int typeIndex = random.nextInt(VehicleThread.Type.values().length);
            VehicleThread vt = new VehicleThread(VehicleThread.Type.values()[typeIndex], i, station);
            vt.start();
        }
    }
    
}
