package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class BookServiceTest {

    private BookService bookService;
    private User user;

    @BeforeAll
    public static void initAll() {
        System.out.println("Starting BookServiceTest...");
    }

    @BeforeEach
    public void setUp() {
        bookService = new BookService();
        user = new User("JohnDoe", "password", "johndoe@example.com");
        System.out.println("Setting up before each test...");
    }

    @Test
    public void testSearchBook_Success() {
        Book book1 = new Book("1984", "George Orwell", "Dystopian", 9.99);
        Book book2 = new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 14.99);
        bookService.addBook(book1);
        bookService.addBook(book2);

        List<Book> result = bookService.searchBook("1984");
        assertEquals(1, result.size());
        assertEquals("1984", result.get(0).getTitle());
    }

    @Test
    public void testSearchBook_NoResults() {
        List<Book> result = bookService.searchBook("NonExistentBook");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSearchBook_EmptyKeyword() {
        List<Book> result = bookService.searchBook("");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testPurchaseBook_Success() {
        Book book = new Book("1984", "George Orwell", "Dystopian", 9.99);
        bookService.addBook(book);

        boolean result = bookService.purchaseBook(user, book);
        assertTrue(result);
    }

    @Test
    public void testPurchaseBook_Failure_BookNotInDatabase() {
        Book book = new Book("NonExistentBook", "Unknown Author", "Unknown Genre", 0.0);

        boolean result = bookService.purchaseBook(user, book);
        assertFalse(result);
    }

    @Test
    public void testPurchaseBook_Failure_NullUserOrBook() {
        boolean result1 = bookService.purchaseBook(null, new Book("1984", "George Orwell", "Dystopian", 9.99));
        boolean result2 = bookService.purchaseBook(new User("JohnDoe", "password", "johndoe@example.com"), null);
        assertFalse(result1);
        assertFalse(result2);
    }

    @Test
    public void testAddBookReview_Success() {
        Book book = new Book("1984", "George Orwell", "Dystopian", 9.99);
        user.getPurchasedBooks().add(book);
        bookService.addBook(book);

        boolean result = bookService.addBookReview(user, book, "Amazing book!");
        assertTrue(result);
        assertEquals(1, book.getReviews().size());
    }

    @Test
    public void testAddBookReview_Failure_NotPurchased() {
        Book book = new Book("1984", "George Orwell", "Dystopian", 9.99);
        bookService.addBook(book);

        boolean result = bookService.addBookReview(user, book, "Amazing book!");
        assertFalse(result);
    }

    @Test
    public void testAddBook_Success() {
        Book book = new Book("1984", "George Orwell", "Dystopian", 9.99);
        boolean result = bookService.addBook(book);
        assertTrue(result);
    }

    @Test
    public void testAddBook_Failure_AlreadyExists() {
        Book book = new Book("1984", "George Orwell", "Dystopian", 9.99);
        bookService.addBook(book);
        boolean result = bookService.addBook(book);  // Attempting to add the same book again
        assertFalse(result);
    }

    @Test
    public void testRemoveBook_Success() {
        Book book = new Book("1984", "George Orwell", "Dystopian", 9.99);
        bookService.addBook(book);
        boolean result = bookService.removeBook(book);
        assertTrue(result);
    }

    @Test
    public void testRemoveBook_Failure_NotInDatabase() {
        Book book = new Book("NonExistentBook", "Unknown Author", "Unknown Genre", 0.0);
        boolean result = bookService.removeBook(book);
        assertFalse(result);
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Tearing down after each test...");
    }

    @AfterAll
    public static void tearDownAll() {
        System.out.println("All BookServiceTest tests completed.");
    }

    @Test
    @Disabled("Test is ignored as a demonstration")
    public void testAddBook_Disabled() {
        // This test won't run
        Book book = new Book("1984", "George Orwell", "Dystopian", 9.99);
        boolean result = bookService.addBook(book);
        assertTrue(result);
    }
}
