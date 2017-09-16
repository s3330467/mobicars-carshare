/*
 * Author: Vishal Pradhan
 * Date: 13-09-2017
 */

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vish
 */
public class BookingServiceTest {
    
    static String expecteduser_id = "1";
    static String expectedcar_id = "testCar_id";
    static String expectedstart_date = "testStart_date" ;
    static String expectedstart_time = "";
    static String expectedcollection_date = "";
    static String expectedcollection_time = "";
    static String expectedend_date = "";
    static String expectedend_time = "";
    static double expectedcost  ;
    static double expectedstart_lat  ;
    static double expectedstart_lng ;
    static double expectedend_lat ;
    static double expectedend_lng ;
    
    public BookingServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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
        String booking_id = "";
        Booking expResult = null;
        Booking result = BookingService.getBooking(booking_id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        Car car = null;
        User user = null;
        boolean expResult = false;
        boolean result = BookingService.createBooking(car, user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
