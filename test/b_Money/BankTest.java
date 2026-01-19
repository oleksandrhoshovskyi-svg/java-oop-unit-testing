package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;

	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);

		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
		assertEquals("Nordea", Nordea.getName());
		assertEquals("DanskeBank", DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertSame(SEK, SweBank.getCurrency());
		assertSame(SEK, Nordea.getCurrency());
		assertSame(DKK, DanskeBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		// Note: This test failed at first because Bank.openAccount() did not actually
		// store the new account in accountlist (it called get() instead of put()).
		// After fixing openAccount() to use accountlist.put(...), the test passes.
		SweBank.openAccount("Charlie");
		assertEquals(0, SweBank.getBalance("Charlie").intValue());

		try {
			SweBank.openAccount("Charlie");
			fail("Expected AccountExistsException");
		} catch (AccountExistsException e) {
			// expected
		}
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		// Note: This test revealed that Bank.deposit() had the condition reversed:
		// it threw AccountDoesNotExistException when the account DID exist.
		// The condition was corrected to check for !containsKey before throwing.
		SweBank.deposit("Ulrika", new Money(10000, SEK));
		assertEquals(10000, SweBank.getBalance("Ulrika").intValue());

		SweBank.deposit("Ulrika", new Money(5000, SEK));
		assertEquals(15000, SweBank.getBalance("Ulrika").intValue());
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		// Note: This test failed because Bank.withdraw() called account.deposit(money)
		// instead of account.withdraw(money). Fixing that call made the test pass.
		SweBank.deposit("Bob", new Money(20000, SEK));

		SweBank.withdraw("Bob", new Money(5000, SEK));
		assertEquals(15000, SweBank.getBalance("Bob").intValue());

		SweBank.withdraw("Bob", new Money(15000, SEK));
		assertEquals(0, SweBank.getBalance("Bob").intValue());
	}

	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		assertEquals(0, SweBank.getBalance("Ulrika").intValue());
		SweBank.deposit("Ulrika", new Money(12345, SEK));
		assertEquals(12345, SweBank.getBalance("Ulrika").intValue());
	}

	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		// Note: For same-bank transfer this test initially failed because the
		// overload transfer(from, to, amount) called transfer(from, this, from, amount),
		// using fromaccount as both source and destination. After fixing it to use
		// toaccount as the third argument, the balances update correctly.
		SweBank.deposit("Ulrika", new Money(20000, SEK));
		SweBank.transfer("Ulrika", "Bob", new Money(5000, SEK));

		assertEquals(15000, SweBank.getBalance("Ulrika").intValue());
		assertEquals(5000,  SweBank.getBalance("Bob").intValue());

		// Note: The cross-bank part also helped verify that Bank.transfer() correctly
		// withdraws from the source and deposits to the target bank/account.
		SweBank.transfer("Ulrika", DanskeBank, "Gertrud", new Money(10000, SEK));

		assertEquals(5000, SweBank.getBalance("Ulrika").intValue());
		assertEquals(7500, DanskeBank.getBalance("Gertrud").intValue());
	}

	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		// Note: This test mainly depends on Bank.tick() delegating to Account.tick().
		// It was failing before the bug in Account.tick() (double tp.tick()) was fixed.
		SweBank.deposit("Ulrika", new Money(20000, SEK));
		SweBank.addTimedPayment("Ulrika", "rent", 2, 1, new Money(10000, SEK), Nordea, "Bob");

		int startUlrika = SweBank.getBalance("Ulrika");
		int startBobNordea = Nordea.getBalance("Bob");

		SweBank.tick();
		assertEquals(startUlrika, SweBank.getBalance("Ulrika").intValue());
		assertEquals(startBobNordea, Nordea.getBalance("Bob").intValue());

		SweBank.tick();
		assertEquals(startUlrika - 10000, SweBank.getBalance("Ulrika").intValue());
		assertEquals(startBobNordea + 10000, Nordea.getBalance("Bob").intValue());
	}
}
