
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
public class Vehicle extends Thread implements Runnable {

    public enum Type {
        A, B, C;
    }
    private Type type;
    private long id;
    private VehicleRepairStation station;
    private static final int DRIVING_TIME_MAX = 10*1000;
    
    public Vehicle(Type type, int id, VehicleRepairStation station) {
        this.type = type;
        this.id = id;
        this.station = station;
    }
    
    @Override
    public void run() {
        Random rand = new Random();
        
        while(true) {
            long drivingTime = rand.nextInt(DRIVING_TIME_MAX);

            try {
                this.sleep(drivingTime);
                station.repair(this);
            } catch (InterruptedException ex) {
                System.err.println("Vehicle" + getType() + "[" + getId() + "]: " + ex.getMessage());
            }
        }
    }
    
    @Override
    public long getId() {
        return id;
    }
    
    public Type getType() {
        return type;
    }
}
