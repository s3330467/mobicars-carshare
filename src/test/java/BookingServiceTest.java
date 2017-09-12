/*
 * @author: Rachel
 * Date: 12.9.17
 * Class: BookingServiceTest
 * Description: Performs unit tests on each method from the BookingService class
 */

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Rachel
 */
public class BookingServiceTest {
    
    static String id1 = "id1";
    static String user_id1 = "userid1";
    static String car_id1 = "carid1";
    static String start_date1 = "01/01/2017";
    static String start_time1 = "01:00:00";
    static String collection_date1 = "02/01/2017";
    static String collection_time1 = "01:15:00";
    static String end_date1 = "03/01/2017";
    static String end_time1 = "01:30:00";
    static double cost1 = 11.00;
    static double start_lat1 = 12.345678;
    static double start_lng1 = 90.123456;
    static double end_lat1 = 23.456789;
    static double end_lng1 = 01.234567;
    
    static String id2 = "id2";
    static String user_id2 = "userid2";
    static String car_id2 = "carid2";
    static String start_date2 = "01/01/2017";
    static String start_time2 = "01:00:00";
    static String collection_date2 = "02/01/2017";
    static String collection_time2 = "01:15:00";
    static String end_date2 = "03/01/2017";
    static String end_time2 = "01:30:00";
    static double cost2 = 11.00;
    static double start_lat2 = 12.345678;
    static double start_lng2 = 90.123456;
    static double end_lat2 = 23.456789;
    static double end_lng2 = 01.234567;
    
    static List<Booking> bookingList = new ArrayList<Booking>();
    
    public BookingServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
//       BookingService.createBooking(car_id1, user_id1);
//       BookingService.createBooking(car_id2, user_id1);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getBooking method, of class BookingService.
     */
    @Test
    public void testGetBooking() {
        System.out.println("getBooking");
        String booking_id = "1";
        Booking expResult = "1";
        Booking result = BookingService.getBooking(booking_id);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCurrentBookingByUser_id method, of class BookingService.
     */
    @Test
    public void testGetCurrentBookingByUser_id() {
        System.out.println("getCurrentBookingByUser_id");
        String user_id = "";
        Booking expResult = null;
        Booking result = BookingService.getCurrentBookingByUser_id(user_id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cancelBooking method, of class BookingService.
     */
    @Test
    public void testCancelBooking() {
        System.out.println("cancelBooking");
        String booking_id = "";
        boolean expResult = false;
        boolean result = BookingService.cancelBooking(booking_id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createBooking method, of class BookingService.
     */
    @Test
    public void testCreateBooking() {
        System.out.println("createBooking");
        Car car = new Car("image", "id", "type", "make", "model", "plate_no", 10.00, 12.345678, 23.456789, true);
        User user = new User("1", "Bobo", "Bibi", "bobo@bibi.com", "passwordx", "tklk", "4639082", "9727359", "Bobo Bibi", "91723", "Jan", "2000", "744");
        boolean expResult = true;
        boolean result = BookingService.createBooking(car, user);
        assertEquals(expResult, result);
        }

    /**
     * Test of collectCar method, of class BookingService.
     */
    @Test
    public void testCollectCar() {
        System.out.println("collectCar");
        String booking_id = "";
        boolean expResult = false;
        boolean result = BookingService.collectCar(booking_id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of returnCar method, of class BookingService.
     */
    @Test
    public void testReturnCar() {
        System.out.println("returnCar");
        String booking_id = "";
        boolean expResult = false;
        boolean result = BookingService.returnCar(booking_id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllBookingsByUser_id method, of class BookingService.
     */
    @Test
    public void testGetAllBookingsByUser_id() {
        System.out.println("getAllBookingsByUser_id");
        String user_id = "";
        List<Booking> expResult = null;
        List<Booking> result = BookingService.getAllBookingsByUser_id(user_id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLastCompleteBookingOfUser method, of class BookingService.
     */
    @Test
    public void testGetLastCompleteBookingOfUser() {
        System.out.println("getLastCompleteBookingOfUser");
        String user_id = "";
        Booking expResult = null;
        Booking result = BookingService.getLastCompleteBookingOfUser(user_id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
