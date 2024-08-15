package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;

    @BeforeAll
    public static void initAll() {
        System.out.println("Starting UserServiceTest...");
    }

    @BeforeEach
    public void setUp() {
        userService = new UserService();
        System.out.println("Setting up before each test...");
    }

    @Test
    public void testRegisterUser_Success() {
        User user = new User("JohnDoe", "password", "johndoe@example.com");
        boolean result = userService.registerUser(user);
        assertTrue(result);
    }

    @Test
    public void testRegisterUser_Failure_UserAlreadyExists() {
        User user = new User("JohnDoe", "password", "johndoe@example.com");
        userService.registerUser(user);  // First registration should succeed
        boolean result = userService.registerUser(user);  // Second registration should fail
        assertFalse(result);
    }

    @Test
    public void testRegisterUser_Failure_NullUsername() {
        User user = new User(null, "password", "johndoe@example.com");
        boolean result = userService.registerUser(user);
        assertFalse(result);
    }

    @Test
    public void testLoginUser_Success() {
        User user = new User("JohnDoe", "password", "johndoe@example.com");
        userService.registerUser(user);
        User loggedInUser = userService.loginUser("JohnDoe", "password");
        assertNotNull(loggedInUser);
    }

    @Test
    public void testLoginUser_Failure_WrongPassword() {
        User user = new User("JohnDoe", "password", "johndoe@example.com");
        userService.registerUser(user);
        User loggedInUser = userService.loginUser("JohnDoe", "wrongpassword");
        assertNull(loggedInUser);
    }

    @Test
    public void testLoginUser_Failure_UserNotFound() {
        User loggedInUser = userService.loginUser("NonExistentUser", "password");
        assertNull(loggedInUser);
    }

    @Test
    public void testUpdateUserProfile_Success() {
        User user = new User("JohnDoe", "password", "johndoe@example.com");
        userService.registerUser(user);
        boolean result = userService.updateUserProfile(user, "NewJohnDoe", "newpassword", "newjohndoe@example.com");
        assertTrue(result);
    }

    @Test
    public void testUpdateUserProfile_Failure_UsernameAlreadyExists() {
        User user1 = new User("JohnDoe", "password", "johndoe@example.com");
        User user2 = new User("JaneDoe", "password", "janedoe@example.com");
        userService.registerUser(user1);
        userService.registerUser(user2);
        boolean result = userService.updateUserProfile(user1, "JaneDoe", "newpassword", "newjohndoe@example.com");
        assertFalse(result);
    }

    @Test
    public void testUpdateUserProfile_Failure_NullNewDetails() {
        User user = new User("JohnDoe", "password", "johndoe@example.com");
        userService.registerUser(user);
        boolean result = userService.updateUserProfile(user, null, null, null);
        assertFalse(result);
    }

    @Test
    public void testRegisterUser_ExceptionHandling() {
        // This test is to demonstrate exception handling
        UserService mockService = mock(UserService.class);
        doThrow(new IllegalArgumentException("Invalid User")).when(mockService).registerUser(any(User.class));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mockService.registerUser(new User("InvalidUser", "password", "invalid@example.com"));
        });

        assertEquals("Invalid User", exception.getMessage());
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Tearing down after each test...");
    }

    @AfterAll
    public static void tearDownAll() {
        System.out.println("All UserServiceTest tests completed.");
    }

    @Test
    @Disabled("Test is ignored as a demonstration")
    public void testRegisterUser_Disabled() {
        // This test won't run
        User user = new User("JohnDoe", "password", "johndoe@example.com");
        boolean result = userService.registerUser(user);
        assertTrue(result);
    }
}
