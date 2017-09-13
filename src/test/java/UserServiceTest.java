/*
 *@author: Vishal Pradhan
 * date: 12-09-2017
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
 * @author: Vishal pradhan
 * date: 12-09-17
 */
public class UserServiceTest {
    static String expectedid ="1";
    static String expectedf_name = "sam";
    static String expectedl_name = "with";
    static String expectedemail = "sam@email.com";
    static String expectedpassword = "SF3mpa1hkVGbI0+opjQ5/vayFGthOEFok2htopkvThzpc0i55oxDFDFzE/BNcHeP";
    static String expectedaddress = "allard street";
    static String expectedlicense_no = "s12235465";
    static String expectedphone_no = "4578996541";
    static String expectedcard_name = "sam";
    static String expectedcard_no = "4125789632147854";
    static String expectedexpiry_month = "02";
    static String expectedexpiry_year = "2018";
    static String expectedcvv = "255";
    static double expectedlat = -37.815206;
    static double expectedlng = 144.963937;
    
    public UserServiceTest() {
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
     * @author: Vishal Pradhan
     * date: 12-09-2017
     * Test of getAllUsers method, of class UserService. This checks that the get 
     * method does not return null value
     */
    //works
    @Test
    public void testGetAllUsers() {
        System.out.println("getAllUsers");
        List<User> expResult = null;
        List<User> result = UserService.getAllUsers();
        assertTrue(result.size() > 0);
    }

    /**
     * @author: Vishal Pradhan
     * date: 12-09-2017
     * Test of getUser method, of class UserService using information from the static variables
     * to check if the function works
     */
    //works
    @Test
    public void testGetUser() {
        System.out.println("getUser");
        User result = UserService.getUser(expectedid);
        assertEquals(result.getF_name(), expectedf_name);
        assertEquals(result.getL_name(), expectedl_name);
        assertEquals(result.getEmail(), expectedemail);
        assertEquals(result.getAddress(), expectedaddress);
        assertEquals(result.getLat(), expectedlat, 0.0001);
        assertEquals(result.getLng(), expectedlng, 0.0001);
    }

    /**
     * @author: Vishal Pradhan
     * date: 12-09-2017
     * Test of getUserByEmail method, of class UserService. where user email id is
     * used to verify if the user exists and if so, compares the email and gets the user name
     */
    @Test
    public void testGetUserByEmail() {
        System.out.println("getUserByEmail");
        List<User> userList = User.userList;
        //User result = UserService.getUserByEmail(expectedemail);
         for(int i = 0; i <userList.size(); i++){
              System.out.println(i + "   " + userList.size());
              if (i == userList.size()){
                  fail("user was never found in the list using getUserByEmail()");
              }
             if(userList.get(i).getEmail().equals(expectedemail)) {
               User result = UserService.getUserByEmail(userList.get(i).getId());
                assertEquals(result.getId(), expectedid);
                assertEquals(result.getF_name(), expectedf_name);
                assertEquals(result.getL_name(), expectedl_name);
                assertEquals(result.getEmail(), expectedemail);
                assertEquals(result.getAddress(), expectedaddress);
                assertEquals(result.getLat(), expectedlat);
                assertEquals(result.getLng(), expectedlng);
                break;
             }
         }
    }

    /**
     * @author: Vishal Pradhan
     * date: 12-09-2017
     * Test of getUserById method, of class UserService. where user ID is used to verify
     * if the user exists and if so compares the ID and gets the users details
     */
    //works
    @Test
    public void testGetUserById() {
        System.out.println("getUserById");
        List<User> userList = User.userList;
        for (int i = 1; i <= userList.size(); i++) {
            System.out.println(i + "   " + userList.size());
            if(i == userList.size()){
                fail("user was never found in the list using getUserById()");
                break;
            }
            if (userList.get(i).getId().equals(expectedid)) {
                User result = UserService.getUserById(userList.get(i).getId());
                assertEquals(result.getId(), expectedid);
                assertEquals(result.getF_name(), expectedf_name);
                assertEquals(result.getL_name(), expectedl_name);
                assertEquals(result.getEmail(), expectedemail);
                assertEquals(result.getAddress(), expectedaddress);
                assertEquals(result.getLat(), expectedlat);
                assertEquals(result.getLng(), expectedlng);
                break;
            }
        }
       
    }

    /**
     * @author: Vishal Pradhan
     * date: 12-09-2017
     * Test of createUser method, of class UserService. checks that the user inserted
     * is present in the User lists 
     */
    //works
    @Test
    public void testCreateUser() {
        System.out.println("createUser");
        List<User> userList = User.userList;
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId().equals(expectedid)) {
                User result = userList.get(i);
                assertEquals(result.getF_name(), expectedf_name);
                assertEquals(result.getL_name(), expectedl_name);
                assertEquals(result.getEmail(), expectedemail);
                assertEquals(result.getPassword(), expectedpassword);
                assertEquals(result.getAddress(), expectedaddress);
                assertEquals(result.getLicense_no(), expectedlicense_no);
                assertEquals(result.getPhone_no(), expectedphone_no);
                assertEquals(result.getCard_name(), expectedcard_name);
                assertEquals(result.getCard_no(), expectedcard_no);
                assertEquals(result.getExpiry_month(), expectedexpiry_month);
                assertEquals(result.getExpiry_year(), expectedexpiry_year);
                assertEquals(result.getCvv(), expectedcvv);
                assertEquals(result.getLat(), expectedlat, 0.0001);
                assertEquals(result.getLng(), expectedlng, 0.0001);
            }
        }
       
    }

    /**
     * @author: Vishal Pradhan
     * date: 12-09-2017
     * Test of validateUser method, of class UserService. uses the email address
     * of the user to check if the user is a valid user...
     * compares the password with the password in the database
     */
    //works
    @Test
    public void testValidateUser() {
        System.out.println("validateUser");
        User result = UserService.getUserByEmail(expectedemail);
        assertEquals(result.getEmail(), expectedemail);
        assertEquals(result.getPassword(), expectedpassword);
        
    }

    /**
     * Test of updateUser method, of class UserService.
     */
//    @Test
//    public void testUpdateUser() {
//        System.out.println("updateUser");
//        
//       
//        
//    }
    
}
