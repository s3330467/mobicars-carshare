/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Rachel
 * Date: 30.8.17
 * Class: BookingTest
 * Description: JUnit testing for Booking class
 */
public class BookingTest {
    
    public BookingTest() {
    }
    
    /**
     * Test of toString method, of class Booking.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Booking instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateBookingList method, of class Booking.
     */
//    @Test
//    public void testUpdateBookingList() {
//        System.out.println("updateBookingList");
//        Booking.updateBookingList();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getId method, of class Booking.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Booking instance = new Booking("1", "2", "3", "31.8.17", "12:00");
        String expResult = "1";
        String result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of setId method, of class Booking.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        String id = "1";
        Booking instance = new Booking("1", "2", "3", "31.8.17", "12:00");
        instance.setId(id);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getUser_id method, of class Booking.
     */
    @Test
    public void testGetUser_id() {
        System.out.println("getUser_id");
        Booking instance = new Booking("1", "2", "3", "31.8.17", "12:00");
        String expResult = "2";
        String result = instance.getUser_id();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of setUser_id method, of class Booking.
     */
    @Test
    public void testSetUser_id() {
        System.out.println("setUser_id");
        String user_id = "1";
        Booking instance = new Booking("1", "2", "3", "31.8.17", "12:00");
        instance.setUser_id(user_id);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getCar_id method, of class Booking.
     */
    @Test
    public void testGetCar_id() {
        System.out.println("getCar_id");
        Booking instance = new Booking("1", "2", "3", "31.8.17", "12:00");
        String expResult = "3";
        String result = instance.getCar_id();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of setCar_id method, of class Booking.
     */
    @Test
    public void testSetCar_id() {
        System.out.println("setCar_id");
        String car_id = "3";
        Booking instance = new Booking("1", "2", "3", "31.8.17", "12:00");
        instance.setCar_id(car_id);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getStart_date method, of class Booking.
     */
    @Test
    public void testGetStart_date() {
        System.out.println("getStart_date");
        Booking instance = new Booking("1", "2", "3", "31.8.17", "12:00");
        String expResult = "31.8.17";
        String result = instance.getStart_date();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of setStart_date method, of class Booking.
     */
    @Test
    public void testSetStart_date() {
        System.out.println("setStart_date");
        String start_date = "";
        Booking instance = null;
        instance.setStart_date(start_date);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getStart_time method, of class Booking.
     */
    @Test
    public void testGetStart_time() {
        System.out.println("getStart_time");
        Booking instance = null;
        String expResult = "";
        String result = instance.getStart_time();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setStart_time method, of class Booking.
     */
    @Test
    public void testSetStart_time() {
        System.out.println("setStart_time");
        String start_time = "";
        Booking instance = null;
        instance.setStart_time(start_time);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCollection_date method, of class Booking.
     */
    @Test
    public void testGetCollection_date() {
        System.out.println("getCollection_date");
        Booking instance = null;
        String expResult = "";
        String result = instance.getCollection_date();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCollection_date method, of class Booking.
     */
    @Test
    public void testSetCollection_date() {
        System.out.println("setCollection_date");
        String collection_date = "";
        Booking instance = null;
        instance.setCollection_date(collection_date);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCollection_time method, of class Booking.
     */
    @Test
    public void testGetCollection_time() {
        System.out.println("getCollection_time");
        Booking instance = null;
        String expResult = "";
        String result = instance.getCollection_time();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCollection_time method, of class Booking.
     */
    @Test
    public void testSetCollection_time() {
        System.out.println("setCollection_time");
        String collection_time = "";
        Booking instance = null;
        instance.setCollection_time(collection_time);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEnd_date method, of class Booking.
     */
    @Test
    public void testGetEnd_date() {
        System.out.println("getEnd_date");
        Booking instance = null;
        String expResult = "";
        String result = instance.getEnd_date();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEnd_date method, of class Booking.
     */
    @Test
    public void testSetEnd_date() {
        System.out.println("setEnd_date");
        String end_date = "";
        Booking instance = null;
        instance.setEnd_date(end_date);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEnd_time method, of class Booking.
     */
    @Test
    public void testGetEnd_time() {
        System.out.println("getEnd_time");
        Booking instance = null;
        String expResult = "";
        String result = instance.getEnd_time();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEnd_time method, of class Booking.
     */
    @Test
    public void testSetEnd_time() {
        System.out.println("setEnd_time");
        String end_time = "";
        Booking instance = null;
        instance.setEnd_time(end_time);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCost method, of class Booking.
     */
    @Test
    public void testGetCost() {
        System.out.println("getCost");
        Booking instance = null;
        double expResult = 0.0;
        double result = instance.getCost();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCost method, of class Booking.
     */
    @Test
    public void testSetCost() {
        System.out.println("setCost");
        double cost = 0.0;
        Booking instance = null;
        instance.setCost(cost);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStart_lat method, of class Booking.
     */
    @Test
    public void testGetStart_lat() {
        System.out.println("getStart_lat");
        Booking instance = null;
        double expResult = 0.0;
        double result = instance.getStart_lat();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setStart_lat method, of class Booking.
     */
    @Test
    public void testSetStart_lat() {
        System.out.println("setStart_lat");
        double start_lat = 0.0;
        Booking instance = null;
        instance.setStart_lat(start_lat);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStart_lng method, of class Booking.
     */
    @Test
    public void testGetStart_lng() {
        System.out.println("getStart_lng");
        Booking instance = null;
        double expResult = 0.0;
        double result = instance.getStart_lng();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setStart_lng method, of class Booking.
     */
    @Test
    public void testSetStart_lng() {
        System.out.println("setStart_lng");
        double start_lng = 0.0;
        Booking instance = null;
        instance.setStart_lng(start_lng);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEnd_lat method, of class Booking.
     */
    @Test
    public void testGetEnd_lat() {
        System.out.println("getEnd_lat");
        Booking instance = null;
        double expResult = 0.0;
        double result = instance.getEnd_lat();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEnd_lat method, of class Booking.
     */
    @Test
    public void testSetEnd_lat() {
        System.out.println("setEnd_lat");
        double end_lat = 0.0;
        Booking instance = null;
        instance.setEnd_lat(end_lat);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEnd_lng method, of class Booking.
     */
    @Test
    public void testGetEnd_lng() {
        System.out.println("getEnd_lng");
        Booking instance = null;
        double expResult = 0.0;
        double result = instance.getEnd_lng();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEnd_lng method, of class Booking.
     */
    @Test
    public void testSetEnd_lng() {
        System.out.println("setEnd_lng");
        double end_lng = 0.0;
        Booking instance = null;
        instance.setEnd_lng(end_lng);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
