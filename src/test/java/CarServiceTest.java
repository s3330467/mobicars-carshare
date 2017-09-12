/**
 * @author Alexander Young
 * Date: 12-09-17
 * Class: CarServiceTest
 * Description: performs junit tests on all the methods inside the class CarService
 */

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Alexander Young
 * date:12-09-17
 */
public class CarServiceTest {

    //static test cars expected values
    static String expectedImage = "test.png";
    static String expectedType = "testType";
    static String expectedMake = "testMake";
    static String expectedModel = "testModel";
    static String expectedPlate_no = "TEST1";
    static double expectedHourly_price = 10.2;
    static double expectedLat = 50.4343433;
    static double expectedLng = 56.5353334;
    
    static String expectedImage2 = "test.png";
    static String expectedType2 = "testType";
    static String expectedMake2 = "testMake";
    static String expectedModel2 = "testModel";
    static String expectedPlate_no2 = "TEST2";
    static double expectedHourly_price2 = 10.2;
    static double expectedLat2 = 50.4343433;
    static double expectedLng2 = 56.5353334;
    
    static String expectedImage3 = "test.png";
    static String expectedType3 = "testType2";
    static String expectedMake3 = "testMake2";
    static String expectedModel3 = "testModel2";
    static String expectedPlate_no3 = "TEST3";
    static double expectedHourly_price3 = 10.2;
    static double expectedLat3 = 50.4343433;
    static double expectedLng3 = 56.5353334;

    public CarServiceTest() {
    }

    /*  
     *  @author Alexander Young
     *  date:12-09-17
     *  inserts three test cars using the static expected values into the static 
     *  car arraylist for the tests to use
     */
    @BeforeClass
    public static void setUpClass() {
        CarService.createCar(expectedImage, expectedType, expectedMake, expectedModel, expectedPlate_no, expectedHourly_price, expectedLat, expectedLng);
        CarService.createCar(expectedImage2, expectedType2, expectedMake2, expectedModel2, expectedPlate_no2, expectedHourly_price2, expectedLat2, expectedLng2);
        CarService.createCar(expectedImage3, expectedType3, expectedMake3, expectedModel3, expectedPlate_no3, expectedHourly_price3, expectedLat3, expectedLng3);
    }
    
    /*  
     *  @author Alexander Young
     *  date:12-09-17
        deleted the cars added in the setUpClass and test the deleteCar function
        to make sure they were deleted   
     */
    @AfterClass
    public static void tearDownClass() {
        CarService.deleteCar(expectedPlate_no);
        CarService.deleteCar(expectedPlate_no2);
        CarService.deleteCar(expectedPlate_no3); 
        List<Car> carList = Car.carList;
        for (int i = 0; i < carList.size(); i++) {
            if (carList.get(i).getPlate_no().equals(expectedPlate_no)) {
                fail("car is still in the carList, it was not deleted");
            }
            if (carList.get(i).getPlate_no().equals(expectedPlate_no2)) {
                fail("car is still in the carList, it was not deleted");
            }
            if (carList.get(i).getPlate_no().equals(expectedPlate_no3)) {
                fail("car is still in the carList, it was not deleted");
            }
        }
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /*  
     *  @author Alexander Young
     *  date:12-09-17
     *  Test of getAllCars method, of class CarService. This is a difficult test
     *  to run on a live database, but as this is a simple get method the test
     *  only checks that get all cars does not return null
     */
    @Test
    public void testGetAllCars_ReturnsNotNull() {
        System.out.println("getAllCars");
        List<Car> expResult = null;
        List<Car> result = CarService.getAllCars();
        assertTrue(result.size() > 0);
    }

    /*  
     *  @author Alexander Young
     *  date:12-09-17
     *  Test of createCar method, of class CarService. checks that the car
     *  inserted in the @before method is present in the car list without using
     *  the getCarByPlateNo function
     */
    @Test
    public void testCreateCar() {
        System.out.println("CreateCar");
        List<Car> carList = Car.carList;
        for (int i = 0; i < carList.size(); i++) {
            if (carList.get(i).getPlate_no().equals(expectedPlate_no)) {
                Car result = carList.get(i);
                assertEquals(result.getImage(), expectedImage);
                assertEquals(result.getType(), expectedType);
                assertEquals(result.getMake(), expectedMake);
                assertEquals(result.getPlate_no(), expectedPlate_no);
                assertEquals(result.getHourly_price(), expectedHourly_price, 0.0001);
                assertEquals(result.getLat(), expectedLat, 0.0001);
                assertEquals(result.getLng(), expectedLng, 0.0001);
            }
        }
    }

    /*  
     *  @author Alexander Young
     *  date:12-09-17
     *  Test of getCarByPlate_no method, of class CarService. checks that when a
     *  specific test car with the numberplate TEST42 is fetched from the
     *  database that its data matches hard coded test data.
     */
    @Test
    public void testGetCarByPlate_no_UsingValidPlate_no() {
        System.out.println("getCarByPlate_no");
        Car result = CarService.getCarByPlate_no(expectedPlate_no);
        assertEquals(result.getImage(), expectedImage);
        assertEquals(result.getType(), expectedType);
        assertEquals(result.getMake(), expectedMake);
        assertEquals(result.getPlate_no(), expectedPlate_no);
        assertEquals(result.getHourly_price(), expectedHourly_price, 0.0001);
        assertEquals(result.getLat(), expectedLat, 0.0001);
        assertEquals(result.getLng(), expectedLng, 0.0001);
    }

    /*  
     *  @author Alexander Young
     *  date:12-09-17
     *  Test of getCarByPlate_no method, of class CarService. checks that when a
     *  non-existant car is fetched from the database that it returns null as no
     *  car should be found.
     */
    @Test
    public void testGetCarByPlate_no_UsingNonexistantPlate_no() {
        System.out.println("GetCarByPlate_no_UsingNonexistantPlate_no");
        Car result = CarService.getCarByPlate_no("XXX000");
        assertNull(result);
    }

    /*  
     *  @author Alexander Young
     *  date:12-09-17
     *  Test of getCarByPlate_no method, of class CarService. checks that when a
     *  null numberplate is used to fetch a car from the database that it returns
     *  null as no car should be found.
     */
    @Test
    public void testGetCarByPlate_no_UsingNullPlate_no() {
        System.out.println("GetCarByPlate_no_UsingNullPlate_no");
        Car result = CarService.getCarByPlate_no(null);
        assertNull(result);
    }

    /*  
     *  @author Alexander Young
     *  date:12-09-17
     *   Test of getCarById method, of class CarService.
     *  loops through all the cars and finds the test car
     *  then uses getCarById() using the test cars Id
     *  if the car returned by getCarById is the test car the function works
     */
    @Test
    public void testGetCarById() {
        System.out.println("getCarById");
        List<Car> carList = Car.carList;
        for (int i = 0; i <= carList.size(); i++) {
            System.out.println(i + "   " + carList.size());
            if(i == carList.size()){
                fail("car was never found in car list using getCarById()");
                break;
            }
            if (carList.get(i).getPlate_no().equals(expectedPlate_no)) {
                Car result = CarService.getCarById(carList.get(i).getId());
                assertEquals(result.getImage(), expectedImage);
                assertEquals(result.getType(), expectedType);
                assertEquals(result.getMake(), expectedMake);
                assertEquals(result.getPlate_no(), expectedPlate_no);
                assertEquals(result.getHourly_price(), expectedHourly_price, 0.0001);
                assertEquals(result.getLat(), expectedLat, 0.0001);
                assertEquals(result.getLng(), expectedLng, 0.0001);
                break;
            }
        }
    }

    /*  
     *  @author Alexander Young
     *  date:12-09-17
     *  Test of carSearch method, of class CarService.
     *  If all three input parameters are "empty" it should return an empty arraylist
     */
    @Test
    public void testCarSearch_AllEmptyFields() {
        System.out.println("CarSearch_AllEmptyFields");
        List<Car> result = CarService.carSearch("empty", "empty", "empty");
        assertTrue(result.isEmpty());
    }
    
    /*  
     *  @author Alexander Young
     *  date:12-09-17
     *  Test of carSearch method, of class CarService.
     *  If the type parameter is specified as "testType" it should return 
     *  an arraylist of cars of size two.
     */
    @Test
    public void testCarSearch_SearchByType() {
        System.out.println("CarSearch_SearchByType");
        List<Car> result = CarService.carSearch("testType", "empty", "empty");
        assertEquals(2, result.size());
    }
    
    /*  
     *  @author Alexander Young
     *  date:12-09-17
     *  Test of carSearch method, of class CarService.
     *  If the type parameter is specified as "test123" it should return 
     *  an empty arraylist as there is no type "test123"
     */
    @Test
    public void testCarSearch_SearchByTypeWithInvalidType() {
        System.out.println("CarSearch_SearchByTypeWithInvalidType");
        List<Car> result = CarService.carSearch("type123", "empty", "empty");
        assertTrue(result.isEmpty());
    }
    
    /*  
     *  @author Alexander Young
     *  date:12-09-17
     *  Test of carSearch method, of class CarService.
     *  If the make parameter is specified as "testMake" it should return 
     *  an arraylist of cars of size two.
     */
    @Test
    public void testCarSearch_SearchByMake() {
        System.out.println("CarSearch_SearchByMake");
        List<Car> result = CarService.carSearch("empty", "testMake", "empty");
        assertEquals(2, result.size());
    }
    
    /*  
     *  @author Alexander Young
     *  date:12-09-17
     *  Test of carSearch method, of class CarService.
     *  If the make parameter is specified as "make123" it should return 
     *  an empty arraylist as there is no make "make123"
     */
    @Test
    public void testCarSearch_SearchByMakeWithInvalidMake() {
        System.out.println("CarSearch_SearchByMakeWithInvalidMake");
        List<Car> result = CarService.carSearch("empty", "make123", "empty");
        assertTrue(result.isEmpty());
    }
    
    /*  
     *  @author Alexander Young
     *  date:12-09-17
     *  Test of carSearch method, of class CarService.
     *  If the make parameter is specified as "testMake" and
     *  the type parameter is specified as "testType" it should return
     *  an arraylist of cars of size two.
     */
    @Test
    public void testCarSearch_SearchByTypeAndMake() {
        System.out.println("CarSearch_SearchByTypeAndMake");
        List<Car> result = CarService.carSearch("testType", "testMake", "empty");
        assertEquals(2, result.size());
    }
    
    /*  
     *  @author Alexander Young
     *  date:12-09-17
     *  Test of carSearch method, of class CarService.
     *  If the make parameter is specified as "make123" and
     *  the type parameter is specified as "testType" it should return
     *  an empty arraylist as there is no make "make123"
     */
    @Test
    public void testCarSearch_SearchByTypeAndMakeWithInvalidMake() {
        System.out.println("CarSearch_SearchByTypeAndMakeWithInvalidMake");
        List<Car> result = CarService.carSearch("testType", "make123", "empty");
        assertTrue(result.isEmpty());
    }
    
    /*  
     *  @author Alexander Young
     *  date:12-09-17
     *  Test of carSearch method, of class CarService.
     *  If the make parameter is specified as "testMake" and
     *  the type parameter is specified as "type123" it should return
     *  an empty arraylist as there is no type "type123"
     */
    @Test
    public void testCarSearch_SearchByTypeAndMakeWithInvalidType() {
        System.out.println("CarSearch_SearchByTypeAndMakeWithInvalidType");
        List<Car> result = CarService.carSearch("type123", "testMake", "empty");
        assertTrue(result.isEmpty());
    }
    
    /*  
     *  @author Alexander Young
     *  date:12-09-17
     *  Test of carSearch method, of class CarService.
     *  If the make parameter is specified as "make123" and
     *  the type parameter is specified as "type123" it should return
     *  an empty arraylist as there is no type "type123" or make "make123"
     */
    @Test
    public void testCarSearch_SearchByTypeAndMakeWithInvalidTypeAndMake() {
        System.out.println("CarSearch_SearchByTypeAndMakeWithInvalidTypeAndMake");
        List<Car> result = CarService.carSearch("type123", "make123", "empty");
        assertTrue(result.isEmpty());
    }
    
    
    
    /*  
     *  @author Alexander Young
     *  date:12-09-17
     *  Test of carSearch method, of class CarService.
     *  If the make parameter is specified as "testMake" and
     *  the type parameter is specified as "testType" and
     *  the model parameter is specified as "testModel" it should return
     *  an arraylist of cars of size two.
     */
    @Test
    public void testCarSearch_SearchByTypeAndMakeAndModel() {
        System.out.println("CarSearch_SearchByTypeAndMakeAndModel");
        List<Car> result = CarService.carSearch("testType", "testMake", "testModel");
        assertEquals(2, result.size());
    }
    
    /*  
     *  @author Alexander Young
     *  date:12-09-17
     *  Test of carSearch method, of class CarService.
     *  If the make parameter is specified as "testMake" and
     *  the type parameter is specified as "testType" and
     *  the model parameter is specified as "model123" it should return
     *  an empty arraylist as "model123" is not a valid model
     */
    @Test
    public void testCarSearch_SearchByTypeAndMakeAndModelWithInvalidModel() {
        System.out.println("CarSearch_SearchByTypeAndMakeAndModelWithInvalidModel");
        List<Car> result = CarService.carSearch("testType", "testMake", "model123");
        assertTrue(result.isEmpty());
    }
}
