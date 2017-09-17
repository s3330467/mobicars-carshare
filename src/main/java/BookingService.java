
import org.sql2o.*;
import java.util.*;
import java.text.*;
import java.text.SimpleDateFormat;

/**
 * Date: 17.8.17
 * <p>
 * Contains methods that manipulate booking information and calls SQL methods in
 * the DB class to insert or retrieve booking data
 *
 * @author Rachel Tan
 */
public class BookingService {

    private static final DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat time = new SimpleDateFormat("HH:mm:ss");

    /**
     * Author: <b>Rachel Tan</b><p>
     * Date: 17.8.17
     * <p>
     * returns a single booking by id by looping through the bookingList array
     *
     * @param booking_id the unique id of a booking to be retrieved from the
     * arraylist
     * @return a single Booking object that matches the booking_id parameter
     */
    public static Booking getBooking(String booking_id) {
        int i;
        Booking.updateBookingList();
        for (i = 0; i < Booking.bookingList.size(); i++) {
            if (Booking.bookingList.get(i).getId().equals(booking_id)) {
                return Booking.bookingList.get(i);
            }
        }
        return null;
    }

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 17.8.17
     * <p>
     * returns a current, unfinished booking of a user. if the user has a
     * booking with no end date this would mean they had started a booking but
     * not finished it. checks that a user only has one current booking before
     * returning the booking.
     *
     * @param user_id the unique id of a user to find the current booking for
     * @return an array of the users current bookings,
     * <p>
     * there should only ever be 1 booking in this array, but its size is
     * checked to be 1 in case things go wrong.
     */
    public static Booking getCurrentBookingByUser_id(String user_id) {
        Booking.updateBookingList();
        if (DB.fetchCurrentBookingByUser_id(user_id).size() == 1) {
            return DB.fetchCurrentBookingByUser_id(user_id).get(0);
        }
        return null;
    }

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 17.8.17
     * <p>
     * when user cancels booking, the cancelBooking method from DB.java is
     * called<p>
     *
     * Updated 22.8.17 by Rachel Tan<p>
     * updates start time and date instead of end time and date<p>
     *
     * @param booking_id the unique id of the booking to cancel
     * @return true if the booking is canceled successfully, otherwise false
     */
    public static boolean cancelBooking(String booking_id) {
        Date current_date = new Date();
        Booking.updateBookingList();
        Booking booking = getBooking(booking_id);
        Car car = CarService.getCarById(booking.getCar_id());
        String end_date = date.format(current_date);
        String end_time = time.format(current_date);
        if (DB.cancelBooking(booking_id, end_date, end_time)) {
            DB.updateCarAvailable(car.getPlate_no(), true);
            Car.updateCarList();
            Booking.updateBookingList();
            return true;
        }
        return false;
    }

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 17.8.17
     * <p>
     * when user confirms booking and car availability is true, the car
     * availability is set to false, and the insertBooking method from DB.java
     * is called.<p>
     *
     * @param car the car that is being booked
     * @param user the user making the booking
     * @return true if the booking is created successfully, otherwise false
     */
    public static boolean createBooking(Car car, User user) {
        int user_id = Integer.parseInt(user.getId());
        int car_id = Integer.parseInt(car.getId());
        Date current_date = new Date();
        String start_date = date.format(current_date);
        String start_time = time.format(current_date);
        double start_lat = car.getLat();
        double start_lng = car.getLng();
        if (car.isAvailable());
        if (DB.insertBooking(user_id, car_id, start_date, start_time, start_lat, start_lng)) {
            DB.updateCarAvailable(car.getPlate_no(), false);
            Car.updateCarList();
            Booking.updateBookingList();
            return true;
        }
        return false;
    }

    /**
     * Author: <b>Rachel Tan</b><p>
     * Date: 22.8.17
     * <p>
     * When user collects a car they have booked, the array bookingList is
     * looped to locate their booking and the collectCar method from DB.java is
     * called, which inserts the collection date and time values into the
     * bookings table on the database<p>
     *
     * Updated 22.8.17 by Rachel Tan<p>
     * Now inserts the values collection time and date instead of overwriting
     * start time and date.<p>
     *
     * Updated 24.8.17 by Rachel Tan<p>
     * Corrected String end_time typo.<p>
     *
     * @param booking_id the id of the booking which is having its car collected
     * @return true if the collection is processed successfully, otherwise false
     */
    public static boolean collectCar(String booking_id) {
        Date current_date = new Date();
        String end_time = time.format(current_date);
        String collection_date = date.format(current_date);
        String collection_time = time.format(current_date);
        int i;
        Booking.updateBookingList();
        for (i = 0; i < Booking.bookingList.size(); i++) {
            if (Booking.bookingList.get(i).getId().equals(booking_id)) {
                DB.collectCar(booking_id, collection_date, collection_time);
                Car.updateCarList();
                Booking.updateBookingList();
                return true;
            }
        }
        return false;
    }

    /**
     * Author: <b>Rachel Tan</b><p>
     * Date: 22.8.17
     * <p>
     * when user returns their booked car, the array bookingList is looped to
     * locate their booking and the returnCar method from DB.java is called,
     * which inserts the end date and time and end location values in the
     * bookings table on the database. The car availability is set to true.
     *
     * @param booking_id the id of the booking which is having its car returned
     * @return true if the car return is processed successfully, otherwise false
     */
    public static boolean returnCar(String booking_id) {
        Date current_date = new Date();
        String end_date = date.format(current_date);
        String end_time = time.format(current_date);
        Booking.updateBookingList();
        Booking booking = getBooking(booking_id);
        Car car = CarService.getCarById(booking.getCar_id());
        double end_lat = car.getLat();
        double end_lng = car.getLng();
        int i;
        for (i = 0; i < Booking.bookingList.size(); i++) {
            if (Booking.bookingList.get(i).getId().equals(booking_id)) {
                DB.returnCar(booking_id, end_date, end_time, end_lat, end_lng);
                DB.updateCarAvailable(car.getPlate_no(), true);
                Car.updateCarList();
                Booking.updateBookingList();
                return true;
            }
        }
        return false;
    }

    /**
     * Author: <b>Rachel Tan</b><p>
     * Date: 22.8.17
     * <p>
     * displays all bookings made by a user according to their id, by calling
     * the fetchAllBookingsByUser_id method from DB.java<p>
     *
     * @param user_id the user id of the user to get a list of bookings for
     * @return an arraylist of Booking objects, containing all of the users
     * bookings
     */
    public static List<Booking> getAllBookingsByUser_id(String user_id) {
        Booking.updateBookingList();
        return DB.fetchAllBookingsByUser_id(user_id);
    }

    /**
     * Author: <b>Rachel Tan</b><p>
     * Date: 7.9.17
     * <p>
     * Calls fetchLastCompleteBookingOfUser from DB.java. Code not working.<p>
     *
     * Updated 7.9.17 by Rachel Tan<p>
     * Fixed with working code.<p>
     *
     * @param user_id the user id of the user to get the last complete booking
     * for
     * @return an arraylist of Booking objects, containing only the users most
     * recent completed booking
     */
    public static Booking getLastCompleteBookingOfUser(String user_id) {
        if (DB.fetchLastCompleteBookingOfUser(user_id).size() == 1) {
            return DB.fetchLastCompleteBookingOfUser(user_id).get(0);
        }
        return null;
    }

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 17.9.17
     * <p>
     * calculates the total cost of a completed booking<p>
     *
     * Updated 17.9.17 by Alexander Young<p>
     * renamed method to calculateTotalCostofBooking from getTotalCostofBooking() as the method isn't really a getter method<p>
     * 
     * @param booking the booking to calculate total cost for
     * @return the total cost of the completed booking
     */
    public static double calculateTotalCostOfBooking(Booking booking) {
        //initialise the duration to 0;
        long durationOfBooking = 0;
        //create a dateFormat object that matches the date formats used in the database
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //parse the separate time and date strings stored in the database into a single Date object
            Date collectionDateTime = df.parse(booking.getCollection_date() + " " + booking.getCollection_time());
            Date endDateTime = df.parse(booking.getEnd_date() + " " + booking.getEnd_time());
            /**
             * calculate the duration of the booking in seconds by subracting
             * the collection time from the end time. this result is given in
             * milliseconds, so divide it by 1000 to turn it into seconds
             */
            durationOfBooking = ((endDateTime.getTime() - collectionDateTime.getTime()) / 1000);
        } catch (ParseException e) {
            System.out.println("could not parse dates in getTotalCostOfBooking()");
        }
        //get the car that was booked so that we can get price information from it
        Car bookedCar = CarService.getCarById(booking.getCar_id());
        //divide the hourly rate of the car by 60 twice to get the price per second
        double pricePerSecond = (bookedCar.getHourly_price() / 60) / 60;
        //multiply the price per second with the duration in seconds to get the cost of the booking
        return pricePerSecond * durationOfBooking;
    }
}
