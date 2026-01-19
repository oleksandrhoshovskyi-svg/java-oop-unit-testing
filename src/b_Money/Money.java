package b_Money;

public class Money implements Comparable {
	private int amount;
	private Currency currency;

	Money (Integer amount, Currency currency) {
		this.amount = amount;
		this.currency = currency;
	}

	public Integer getAmount() {
		return amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	@Override
	public String toString() {
		int abs = Math.abs(amount);
		int units = abs / 100;
		int cents = abs % 100;
		String sign = amount < 0 ? "-" : "";
		return String.format("%s%d.%02d %s", sign, units, cents, currency.getName());
	}

	public Integer universalValue() {
		return currency.universalValue(amount);
	}

	public Boolean equals(Money other) {
		if (other == null) return false;
		return this.universalValue().equals(other.universalValue());
	}

	public Money add(Money other) {
		int otherInThis = currency.valueInThisCurrency(other.amount, other.currency);
		return new Money(this.amount + otherInThis, this.currency);
	}

	public Money sub(Money other) {
		int otherInThis = currency.valueInThisCurrency(other.amount, other.currency);
		return new Money(this.amount - otherInThis, this.currency);
	}

	public Boolean isZero() {
		return amount == 0;
	}

	public Money negate() {
		return new Money(-amount, currency);
	}

	@Override
	public int compareTo(Object other) {
		Money o = (Money) other;
		return this.universalValue().compareTo(o.universalValue());
	}
}
