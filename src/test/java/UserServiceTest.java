/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Rachel
 */
public class UserServiceTest {
    
    public UserServiceTest() {
    }

    /**
     * Test of getAllUsers method, of class UserService.
     */
    @Test
    public void testGetAllUsers() {
        System.out.println("getAllUsers");
        List<User> expResult = null;
        List<User> result = UserService.getAllUsers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getUser method, of class UserService.
     */
    @Test
    public void testGetUser() {
        System.out.println("getUser");
        String id = "2";
        User expResult = "2";
        User result = UserService.getUser(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserByEmail method, of class UserService.
     */
    @Test
    public void testGetUserByEmail() {
        System.out.println("getUserByEmail");
        String email = "test@email.com";
        User expResult = "test@email.com";
        User result = UserService.getUserByEmail(email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserById method, of class UserService.
     */
    @Test
    public void testGetUserById() {
        System.out.println("getUserById");
        String id = "";
        User expResult = null;
        User result = UserService.getUserById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of createUser method, of class UserService.
     */
    @Test
    public void testCreateUser() {
        System.out.println("createUser");
        String email = "test@email.com";
        String userPassword = "test";
        String f_name = "Test";
        String l_name = "Testy";
        String address = "56 dismyadd";
        String license_no = "123456";
        String phone_no = "7890";
        boolean expResult = true;
        boolean result = UserService.createUser("test@email.com", "test", "Test", "Testy", "56 dismyadd", "123456", "7890");
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of validateUser method, of class UserService.
     */
    @Test
    public void testValidateUser() {
        System.out.println("validateUser");
        String email = "test@email.com";
        String inputPassword = "test";
        Boolean expResult = true;
        Boolean result = UserService.validateUser("test@email.com", "test");
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of updateUser method, of class UserService.
     */
    @Test
    public void testUpdateUser() {
        System.out.println("updateUser");
        String id = "";
        String name = "";
        String email = "";
        UserService instance = new UserService();
        User expResult = null;
        User result = instance.updateUser(id, name, email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
}
