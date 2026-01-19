package b_Money;

import java.util.Hashtable;

public class Account {
	private Money content;
	private Hashtable<String, TimedPayment> timedpayments = new Hashtable<String, TimedPayment>();

	Account(String name, Currency currency) {
		this.content = new Money(0, currency);
	}

	public void addTimedPayment(String id, Integer interval, Integer next, Money amount, Bank tobank, String toaccount) {
		TimedPayment tp = new TimedPayment(interval, next, amount, this, tobank, toaccount);
		timedpayments.put(id, tp);
	}

	public void removeTimedPayment(String id) {
		timedpayments.remove(id);
	}

	public boolean timedPaymentExists(String id) {
		return timedpayments.containsKey(id);
	}

	public void tick() {
		for (TimedPayment tp : timedpayments.values()) {
			tp.tick();
		}
	}

	public void deposit(Money money) {
		content = content.add(money);
	}

	public void withdraw(Money money) {
		content = content.sub(money);
	}

	public Money getBalance() {
		return content;
	}

	private class TimedPayment {
		private int interval, next;
		private Account fromaccount;
		private Money amount;
		private Bank tobank;
		private String toaccount;
		
		TimedPayment(Integer interval, Integer next, Money amount, Account fromaccount, Bank tobank, String toaccount) {
			this.interval = interval;
			this.next = next;
			this.amount = amount;
			this.fromaccount = fromaccount;
			this.tobank = tobank;
			this.toaccount = toaccount;
		}

		public Boolean tick() {
			if (next == 0) {
				next = interval;

				fromaccount.withdraw(amount);
				try {
					tobank.deposit(toaccount, amount);
				}
				catch (AccountDoesNotExistException e) {
					fromaccount.deposit(amount);
				}
				return true;
			}
			else {
				next--;
				return false;
			}
		}
	}

}
