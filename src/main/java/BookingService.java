
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
     *
     * Updated 20.9.17 by Alexander Young<p>
     * removed reference to the updateBookingList method, the code first checks
     * if a complete entry exists in the java object list, if not it fetches the
     * matching booking from the SQL database and then adds it to the java
     * object list<p>
     */
    public static Booking getCurrentBookingByUser_id(String user_id) {
        Booking booking;
        for (int i = 0; i < Booking.bookingList.size(); i++) {
            booking = Booking.bookingList.get(i);
            if (Booking.bookingList.get(i).getUser_id().equals(user_id)) {
                if (booking.getEnd_date() == null && booking.getId() != null) {
                    return booking;
                }
            }
        }

        if (DB.fetchCurrentBookingByUser_id(user_id).size() == 1) {
            booking = DB.fetchCurrentBookingByUser_id(user_id).get(0);
            //return DB.fetchCurrentBookingByUser_id(user_id).get(0);
            return booking;
        }
//        Booking booking;
//        for (int i = 0; i < Booking.bookingList.size(); i++) {
//            booking = Booking.bookingList.get(i);
//            System.out.println("User Id of this user is: " + booking.getUser_id());
//            if (Booking.bookingList.get(i).getUser_id().equals(user_id)) {
//                System.out.println("user matched, end date looks like: " + booking.getEnd_date());
//                if (booking.getEnd_date() == null) {
//                    return booking;
//                }
//            }
//        }
        System.out.println("could not find any current booking for user");
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
     *
     * Updated 20.9.17 by Alexander Young<p>
     * removed reference to the updateBookingList and updateUserList methods,
     * the method now updates java objects directly as well as editing the DB<p>
     */
    public static boolean cancelBooking(String booking_id) {
        Date current_date = new Date();
        Booking booking = getBooking(booking_id);
        Car car = CarService.getCarById(booking.getCar_id());
        String end_date = date.format(current_date);
        String end_time = time.format(current_date);
        if (DB.cancelBooking(booking_id, end_date, end_time)) {
            booking.setEnd_date(end_date);
            booking.setEnd_time(end_time);
            DB.updateCarAvailable(car.getPlate_no(), true);
            car.setAvailable(true);
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
     * Updated 20.9.17 by Alexander Young<p>
     * removed reference to the updateBookingList and updateCarList methods, the
     * method now updates java objects directly as well as editing the DB<p>
     *
     * Updated 27.9.17 by Rachel Tan<p>
     * Added expectedDateTime parameter<p>
     *
     * Updated 27.9.17 by Alexander Young<p>
     * reworked the time formatting code to work with the format supplied by the
     * HTML<p>
     *
     * @param car the car that is being booked
     * @param user the user making the booking
     * @param expectedDateTime the date and time selected by user during booking
     * for when they intend to return the car
     * @return true if the booking is created successfully, otherwise false
     */
    public static boolean createBooking(Car car, User user, String expectedDateTime) {
        String user_id = user.getId();
        System.out.println("Creating booking for userid: " + user_id);
        String car_id = car.getId();
        Date current_date = new Date();
        String start_date = date.format(current_date);
        String start_time = time.format(current_date);
        String exp_date = "";
        String exp_time = "";
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        try {
            Date expected_date = df.parse(expectedDateTime);
            exp_date = date.format(expected_date);
            exp_time = time.format(expected_date);
            System.out.print("expected date: " + exp_date);
            System.out.print("expected time: " + exp_time);
        } catch (ParseException e) {
            System.out.println("could not parse dates in createBooking()");
        }

        System.out.println("BookingService exp date: " + exp_date + "exp time: " + exp_time);
        double start_lat = car.getLat();
        double start_lng = car.getLng();
        if (car.isAvailable());
        if (DB.insertBooking(user_id, car_id, start_date, start_time, start_lat, start_lng, exp_date, exp_time)) {
            Booking booking = getCurrentBookingByUser_id(user_id);
            System.out.println(user_id);
            System.out.println("this booking exists and its id is: " + booking.getId());
            Booking.bookingList.add(booking);
            DB.updateCarAvailable(car.getPlate_no(), false);
            car.setAvailable(false);
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
     * Updated 19.9.17 by Alexander Young<p>
     * added method call to begin simulated car movement when car is
     * collected<p>
     *
     * Updated 20.9.17 by Alexander Young<p>
     * removed reference to the updateBookingList method, the method now updates
     * java objects directly as well as editing the DB<p>
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
        Booking booking;
        Car car;
        booking = BookingService.getBooking(booking_id);
        if (DB.collectCar(booking_id, collection_date, collection_time)) {
            car = CarService.getCarById(booking.getCar_id());
            System.out.println(car.getPlate_no());
            booking.setCollection_date(collection_date);
            booking.setCollection_time(collection_time);
//            car.carSim.setCar(car);
            car.carSim = new CarSimulator(car);
            car.carSim.startMoving();
            return true;
        }
        return false;
    }

    /**
     * Author: <b>Rachel Tan</b><p>
     * Date: 28.9.17
     * <p>
     *
     * Updated 29.9.17 by Alexander Young<p>
     * changed the input parameter of extendBooking to use a Booking object
     * rather than a booking id
     *
     * @param booking booking to modify
     * @param expectedDateTime the new date and time selected by user when
     * extending booking for when they intend to return the car
     * @return true if expected date and time are updated, otherwise false
     */
    public static boolean extendBooking(Booking booking, String expectedDateTime) {
        //String exp_date = booking.getExp_date();
        //String exp_time = booking.getExp_time();
        String new_exp_date = "";
        String new_exp_time = "";
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        try {
            Date new_expected_date = df.parse(expectedDateTime);
            new_exp_date = date.format(new_expected_date);
            new_exp_time = time.format(new_expected_date);
            System.out.print("new expected date: " + new_exp_date);
            System.out.print("new expected time: " + new_exp_time);
        } catch (ParseException e) {
            System.out.println("could not parse dates in extendBooking()");
        }
        if (DB.updateExpDateTime(booking.getId(), new_exp_date, new_exp_time)) {
            booking.setExp_date(new_exp_date);
            booking.setExp_time(new_exp_time);
            return true;
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
     * bookings table on the database. The car availability is set to true.<p>
     *
     * Updated 20.9.17 by Alexander Young<p>
     * removed reference to the updateBookingList and updateCarList methods, the
     * method now updates java objects directly as well as editing the DB<p>
     *
     * @param booking_id the id of the booking which is having its car returned
     * @return true if the car return is processed successfully, otherwise false
     */
    public static boolean returnCar(String booking_id) {
        Date current_date = new Date();
        String end_date = date.format(current_date);
        String end_time = time.format(current_date);
        Booking booking = getBooking(booking_id);
        Car car = CarService.getCarById(booking.getCar_id());
        double end_lat = car.getLat();
        double end_lng = car.getLng();
        if (DB.returnCar(booking_id, end_date, end_time, end_lat, end_lng)) {
            DB.updateCarAvailable(car.getPlate_no(), true);
            car.setAvailable(true);
            booking.setEnd_date(end_date);
            booking.setEnd_time(end_time);
            booking.setEnd_lat(end_lat);
            booking.setEnd_lng(end_lng);
            car.carSim.stopMoving();
            return true;
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
     * Updated 20.9.17 by Alexander Young<p>
     * removed reference to the updateBookingList method,
     *
     * @param user_id the user id of the user to get a list of bookings for
     * @return an arraylist of Booking objects, containing all of the users
     * bookings
     */
    public static List<Booking> getAllBookingsByUser_id(String user_id) {
        List<Booking> usersBookings = new ArrayList<Booking>();
        for (int i = 0; i < Booking.bookingList.size(); i++) {
            if (Booking.bookingList.get(i).getUser_id().equals(user_id)) {
                usersBookings.add(Booking.bookingList.get(i));
            }
        }
        return usersBookings;
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
     * Updated 20.9.17 by Alexander Young<p>
     * the method now uses the SQL output to help find a specific entry in
     * bookingList rather than returning a completely new booking object<p>
     *
     * @param user_id the user id of the user to get the last complete booking
     * for
     * @return an arraylist of Booking objects, containing only the users most
     * recent completed booking
     */
    public static Booking getLastCompleteBookingOfUser(String user_id) {
        if (DB.fetchLastCompleteBookingOfUser(user_id).size() == 1) {
            Booking booking = getBooking(DB.fetchLastCompleteBookingOfUser(user_id).get(0).getId());
            return booking;
        }
        return null;
    }

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 17.9.17
     * <p>
     * calculates the total cost of a completed booking based on the number of
     * seconds elapsed, using car's hourly price as the baseline.<p>
     *
     * Updated 17.9.17 by Alexander Young<p>
     * renamed method to calculateTotalCostofBooking from
     * getTotalCostofBooking() as the method isn't really a getter method<p>
     *
     * Updated 19.9.17 by Rachel Tan<p>
     * Total cost now returns a number rounded to 2 decimal places instead of
     * multiple decimal places.<p>
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
             * calculate the duration of the booking in seconds by subtracting
             * the collection time from the end time. this result is given in
             * milliseconds, so divide it by 1000 to turn it into seconds
             */
            durationOfBooking = ((endDateTime.getTime() - collectionDateTime.getTime()) / 1000);
        } catch (ParseException e) {
            System.out.println("could not parse dates in calculateTotalCostOfBooking()");
        }
        //get the car that was booked so that we can get price information from it
        Car bookedCar = CarService.getCarById(booking.getCar_id());
        //divide the hourly rate of the car by 60 twice to get the price per second
        double pricePerSecond = (bookedCar.getHourly_price() / 60) / 60;
        //multiply the price per second with the duration in seconds to get the cost of the booking
        double totalCost = pricePerSecond * durationOfBooking;
        totalCost = Math.round(totalCost * 100);
        totalCost = totalCost / 100;
        double penaltyCost = calculatePenaltyCostOfBooking(booking);
        return totalCost + penaltyCost;
    }

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 29.9.17
     * <p>
     * calculates the cost of the penalty time a booking has<p>
     *
     * @param booking the booking to calculate penalty cost
     * @return the total penalty cost
     */
    public static double calculatePenaltyCostOfBooking(Booking booking) {
        //initialise the duration to 0;
        long durationOfBooking = 0;
        //create a dateFormat object that matches the date formats used in the database
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //parse the separate time and date strings stored in the database into a single Date object
            Date expectedDateTime = df.parse(booking.getExp_date() + " " + booking.getExp_time());
            Date endDateTime = df.parse(booking.getEnd_date() + " " + booking.getEnd_time());
            /**
             * calculate the duration of the booking in seconds by subtracting
             * the collection time from the end time. this result is given in
             * milliseconds, so divide it by 1000 to turn it into seconds
             */
            durationOfBooking = ((endDateTime.getTime() - expectedDateTime.getTime()) / 1000);
        } catch (ParseException e) {
            System.out.println("could not parse dates in calculateTotalCostOfBooking()");
        }
        //get the car that was booked so that we can get price information from it
        Car bookedCar = CarService.getCarById(booking.getCar_id());
        //divide the hourly rate of the car by 60 twice to get the price per second
        double pricePerSecond = (bookedCar.getHourly_price() / 60) / 60;
        //multiply the price per second with the duration in seconds to get the cost of the booking
        double totalCost = pricePerSecond * durationOfBooking;
        totalCost = Math.round(totalCost * 100);
        totalCost = totalCost / 100;
        return totalCost;
    }

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 17.9.17
     * <p>
     * calculates the elapsed duration in seconds between the collection and
     * return of a booking<p>
     *
     * @param booking the booking to calculate duration
     * @return the duration in seconds of the booking
     */
    public static int calculateBookingSeconds(Booking booking) {
        //initialise the duration to 0;
        long durationOfBooking = 0;
        //create a dateFormat object that matches the date formats used in the database
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //parse the separate time and date strings stored in the database into a single Date object
            Date collectionDateTime = df.parse(booking.getCollection_date() + " " + booking.getCollection_time());
            Date endDateTime = df.parse(booking.getEnd_date() + " " + booking.getEnd_time());
            /**
             * calculate the duration of the booking in seconds by subtracting
             * the collection time from the end time. this result is given in
             * milliseconds, so divide it by 1000 to turn it into seconds
             */
            durationOfBooking = ((endDateTime.getTime() - collectionDateTime.getTime()) / 1000);
        } catch (ParseException e) {
            System.out.println("could not parse dates in calculateBookingDuration()");
        }
        int durationInSeconds = (int) durationOfBooking;
        return durationInSeconds;
    }

    /**
     * Author: <b>Alexander Young</b><p>
     * Date: 17.9.17
     * <p>
     * calculates the elapsed duration in hours between the collection and
     * return of a booking<p>
     *
     * Updated 19.9.17 by Rachel Tan<p>
     * Time is reduced to 2 decimal places.<p>
     *
     * @param booking the booking to calculate duration
     * @return the duration in hours of the booking
     */
    public static double calculateBookingHours(Booking booking) {
        //cast the time in seconds to double so it can be divided
        double durationInSeconds = (double) calculateBookingSeconds(booking);
        //divide the result by 60 twice to convert it to hours
        durationInSeconds = ((durationInSeconds) / 60) / 60;
        durationInSeconds = Math.round(durationInSeconds * 100);
        durationInSeconds = durationInSeconds / 100;
        return durationInSeconds;
    }

    /**
     * Author: <b>Rachel Tan</b><p>
     * Date 19.9.17
     * <p>
     *
     * Calls method that calculates the total cost of booking then calls DB
     * method which updates the cost of the booking by ID<p>
     *
     * @param booking_id The ID of the user's current booking
     * @return True if cost is updated, otherwise false
     *
     * Updated 21.9.17 by Rachel Tan<p>
     * Changed the method called that retrieves booking ID<p>
     */
    public static boolean insertTotalCostOfBookingById(String booking_id) {
        Booking booking = getBooking(booking_id);
        booking.setCost(BookingService.calculateTotalCostOfBooking(booking));
        double cost = booking.getCost();

        if (DB.updateTotalCostOfBooking(booking_id, cost)) {
            return true;
        }
        return false;
    }
}
