package p.concurrent;

public class UnSynchTest {
	private static final int NACOUNT = 100;
	private static final double INIT_BALANCE = 1000;

	public static void main(String[] args) {
		Bank bank = new Bank(NACOUNT, INIT_BALANCE);
		for (int i = 0; i < NACOUNT; i++) {
			TransferRunnable r = new TransferRunnable(bank, i, 100);
			new Thread(r).start();
		}
	}

	static class Bank {
		private double[] accounts;

		public Bank(int n, double b) {
			accounts = new double[n];
			for (int i = 0; i < n; i++)
				accounts[i] = b;
		}

		public void transfer(int from, int to, double amount) {
			if (accounts[from] < amount)
				return;
			System.out.printf("%10.2f from %d to %d\n", amount, from, to);
			accounts[from] -= amount;
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			accounts[to] += amount;
			System.out.printf("Total balance %10.2f\n", getTotalBalance());
		}

		public double getTotalBalance() {
			double sum = 0;
			for (double d : accounts)
				sum += d;
			return sum;
		}

		public int size() {
			return accounts.length;
		}
	}

	static class TransferRunnable implements Runnable {
		private Bank bank;
		private int from;
		private double maxAmount;

		public TransferRunnable(Bank bank, int from, double maxAmount) {
			this.bank = bank;
			this.from = from;
			this.maxAmount = maxAmount;
		}

		@Override
		public void run() {
			while (true) {
				int to = (int) (bank.size() * Math.random());
				double amount = maxAmount * Math.random();
				bank.transfer(from, to, amount);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
