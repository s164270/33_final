package player;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    private Account account;

    @BeforeEach
    void setUp() { account = new Account(1000);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void withdraw() {
        account.withdraw(600);
        assertEquals(account.getBalance(), 400);

        assertEquals(account.withdraw(-250), 0);
        assertEquals(account.getBalance(), 400);

        assertEquals(account.withdraw(500), 400);
        assertEquals(account.getBalance(), 0);
    }

    @Test
    void deposit() {
        assertTrue(account.deposit(100));
        assertEquals(account.getBalance(), 1100);
        assertFalse(account.deposit(-200));
        assertEquals(account.getBalance(), 1100);
    }

    @Test
    void testToString() {
        assertEquals(account.toString(), "$1000");
        account.deposit(123);
        assertEquals(account.toString(), "$1123");
    }

    @Test
    void getBalance() {
        assertEquals(account.getBalance(), 1000);
    }
}