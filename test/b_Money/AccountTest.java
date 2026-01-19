package b_Money;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK;
	Bank SweBank;
	Account testAccount;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");

		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}

	@Test
	public void testAddRemoveTimedPayment() {
		assertFalse(testAccount.timedPaymentExists("rent"));

		Money rent = new Money(10000, SEK);
		testAccount.addTimedPayment("rent", 2, 1, rent, SweBank, "Alice");
		assertTrue(testAccount.timedPaymentExists("rent"));

		testAccount.removeTimedPayment("rent");
		assertFalse(testAccount.timedPaymentExists("rent"));
	}

	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		// Note: This test initially failed because Account.tick() called tp.tick() twice
		// for each time unit. As a result, the payment was triggered too early and
		// balances changed already after the first tick.
		// Fix: in Account.tick() we now call tp.tick() only once per time unit.
		Money rent = new Money(10000, SEK);

		testAccount.addTimedPayment("rent", 2, 1, rent, SweBank, "Alice");

		int startHans  = testAccount.getBalance().getAmount();
		int startAlice = SweBank.getBalance("Alice");

		testAccount.tick();
		assertEquals("First tick: Hans unchanged",
				startHans, testAccount.getBalance().getAmount().intValue());
		assertEquals("First tick: Alice unchanged",
				startAlice, SweBank.getBalance("Alice").intValue());

		testAccount.tick();
		assertEquals("Hans should have paid 100 SEK once",
				startHans - 10000, testAccount.getBalance().getAmount().intValue());
		assertEquals("Alice should have received 100 SEK once",
				startAlice + 10000, SweBank.getBalance("Alice").intValue());
	}

	@Test
	public void testAddWithdraw() {
		int start = testAccount.getBalance().getAmount();

		testAccount.deposit(new Money(5000, SEK));
		assertEquals(start + 5000, testAccount.getBalance().getAmount().intValue());

		testAccount.withdraw(new Money(3000, SEK));
		assertEquals(start + 2000, testAccount.getBalance().getAmount().intValue());
	}

	@Test
	public void testGetBalance() {
		assertEquals(10_000_000, testAccount.getBalance().getAmount().intValue());

		testAccount.deposit(new Money(1, SEK));
		assertEquals(10_000_001, testAccount.getBalance().getAmount().intValue());
	}
}
