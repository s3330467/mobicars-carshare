
import java.util.Timer;
import java.util.TimerTask;

/**
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

    public void startMoving() {
        movementState = true;
        timer.schedule(new moveCar(), 0, 1000);
    }

    public void stopMoving() {
        movementState = false;
        timer.cancel();
    }

    class moveCar extends TimerTask {

        public void run() {
            randResult = rand.nextDouble();
            if(randResult < 0.05) {
                System.out.print("result was :" + randResult + " 20% chance triggered");
                latMovement = ((rand.nextDouble()-0.5)*0.0005);
                lngMovement = ((rand.nextDouble()-0.5)*0.0005);
            }
            System.out.println(lngMovement);
            System.out.println(latMovement);
            newLat += latMovement;
            newLng += lngMovement;
            car = CarService.getCarByPlate_no(car.getPlate_no());
            System.out.println("modifying car: " + car.getPlate_no() + " with new lat/lng: " + newLat + " " + newLng);
            CarService.updateCarLatLng(car.getPlate_no(), newLat,  newLng);
        }
    }
}
