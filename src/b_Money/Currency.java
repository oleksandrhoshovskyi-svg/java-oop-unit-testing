package b_Money;

public class Currency {
	private String name;
	private Double rate;

	Currency (String name, Double rate) {
		this.name = name;
		this.rate = rate;
	}

	public Integer universalValue(Integer amount) {
		return (int) Math.round(amount * rate);
	}

	public String getName() {
		return name;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Integer valueInThisCurrency(Integer amount, Currency othercurrency) {
		if (this == othercurrency) {
			return amount;
		}

		int universal = othercurrency.universalValue(amount); // already rounded int
		double inThis = universal / this.rate;
		return (int) Math.round(inThis);
	}
}
