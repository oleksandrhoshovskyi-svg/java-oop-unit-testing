package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);

		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100= new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		assertEquals(Integer.valueOf(10000), SEK100.getAmount());
		assertEquals(Integer.valueOf(1000), EUR10.getAmount());
		assertEquals(Integer.valueOf(0), SEK0.getAmount());
	}

	@Test
	public void testGetCurrency() {
		assertSame(SEK, SEK100.getCurrency());
		assertSame(EUR, EUR10.getCurrency());
		assertSame(SEK, SEKn100.getCurrency());
	}

	@Test
	public void testToString() {
		assertEquals("100.00 SEK", SEK100.toString());
		assertEquals("10.00 EUR",  EUR10.toString());
		assertEquals("-100.00 SEK", SEKn100.toString());
		assertEquals("0.00 SEK",   SEK0.toString());
	}

	@Test
	public void testGlobalValue() {
		assertEquals(Integer.valueOf(1500), SEK100.universalValue());
		assertEquals(Integer.valueOf(1500), EUR10.universalValue());
		assertEquals(Integer.valueOf(3000), EUR20.universalValue());
	}

	@Test
	public void testEqualsMoney() {
		assertTrue(SEK100.equals(EUR10));
		assertTrue(EUR10.equals(SEK100));
		assertFalse(SEK100.equals(SEK200));
		assertFalse(SEK100.equals(SEK0));
	}

	@Test
	public void testAdd() {
		Money sum1 = SEK100.add(EUR10);
		assertEquals(Integer.valueOf(20000), sum1.getAmount());
		assertSame(SEK, sum1.getCurrency());

		Money sum2 = EUR10.add(SEK100);
		assertEquals(Integer.valueOf(2000), sum2.getAmount());
		assertSame(EUR, sum2.getCurrency());
	}

	@Test
	public void testSub() {
		Money diff1 = SEK200.sub(EUR10);
		assertEquals(Integer.valueOf(10000), diff1.getAmount());
		assertSame(SEK, diff1.getCurrency());

		Money diff2 = SEK100.sub(EUR20);
		assertEquals(Integer.valueOf(-10000), diff2.getAmount());
		assertSame(SEK, diff2.getCurrency());
	}

	@Test
	public void testIsZero() {
		assertTrue(SEK0.isZero());
		assertTrue(EUR0.isZero());
		assertFalse(SEK100.isZero());
		assertFalse(SEKn100.isZero());
	}

	@Test
	public void testNegate() {
		Money neg = SEK100.negate();
		assertEquals(Integer.valueOf(-10000), neg.getAmount());
		assertSame(SEK, neg.getCurrency());

		Money back = neg.negate();
		assertEquals(SEK100.getAmount(), back.getAmount());
		assertSame(SEK, back.getCurrency());
	}

	@Test
	public void testCompareTo() {
		assertEquals(0, SEK100.compareTo(EUR10));
		assertEquals(0, EUR10.compareTo(SEK100));

		assertTrue(SEK100.compareTo(SEK200) < 0);
		assertTrue(SEK200.compareTo(SEK100) > 0);

		assertTrue(SEKn100.compareTo(SEK0) < 0);
		assertTrue(SEK0.compareTo(SEKn100) > 0);
	}
}
