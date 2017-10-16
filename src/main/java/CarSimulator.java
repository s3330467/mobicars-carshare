
import java.util.Timer;
import java.util.TimerTask;

/**
 * Date: 20.9.17
 * <p>
 * contains methods that simulate the movement of a car on the map by randomly updating its latitude and longitude
 *
 * @author Alexander Young
 */
public class CarSimulator {

    //movement state true for car is moving, false for car should not move.
    boolean movementState = false;
    Car car;
    Timer timer = new Timer();
    java.util.Random rand = new java.util.Random();
    double latMovement = ((rand.nextDouble()-0.5)*0.01);
    double lngMovement = ((rand.nextDouble()-0.5)*0.01);
    double newLat;
    double newLng;
    double randResult;
    
    public CarSimulator() {
    }
    
    public CarSimulator(Car car) {
        this.car = car;
        newLat = car.getLat();
        newLng = car.getLng();
    }
    
    public void setCar(Car car) {
        this.car = car;
        newLat = car.getLat();
        newLng = car.getLng();
    }
    
    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 20.9.17<p>
     * 
     * sets the movementState flag to true and starts a timer that executes the moveCar() method every second
     */
    public void startMoving() {
        movementState = true;
        timer.schedule(new moveCar(), 0, 1000);
    }
    
    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 20.9.17<p>
     * 
     * sets the movementState flag to false cancels the timer that executes the moveCar() method
     */
    public void stopMoving() {
        movementState = false;
        timer.cancel();
    }
    
    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 20.9.17
     * <p>
     * applies scaling factors to random doubles in order to simulate random x and y axis movement
     */
    class moveCar extends TimerTask {

        public void run() {
            randResult = rand.nextDouble();
            if(randResult < 0.05) {
                latMovement = ((rand.nextDouble()-0.5)*0.0005);
                lngMovement = ((rand.nextDouble()-0.5)*0.0005);
            }
            System.out.println(lngMovement);
            System.out.println(latMovement);
            newLat += latMovement;
            newLng += lngMovement;
            car = CarService.getCarByPlate_no(car.getPlate_no());
            CarService.updateCarLatLng(car.getPlate_no(), newLat,  newLng);
        }
    }
}
