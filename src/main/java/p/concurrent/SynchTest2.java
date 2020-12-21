package p.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynchTest2 {
	private static final int NACOUNT = 2;
	private static final double INIT_BALANCE = 1000;

	public static void main(String[] args) {
		Bank bank = new Bank(NACOUNT, INIT_BALANCE);
		for (int i = 0; i < NACOUNT; i++) {
			TransferRunnable r = new TransferRunnable(bank, i, 100);
			new Thread(r).start();
		}
	}

	static class Bank {
		private Lock lock;
		private Condition con;
		private double[] accounts;

		public Bank(int n, double b) {
			accounts = new double[n];
			for (int i = 0; i < n; i++)
				accounts[i] = b;
			lock = new ReentrantLock();
			con = lock.newCondition();
		}

		public void transfer(int from, int to, double amount) throws InterruptedException {
			lock.lock();
			try {
				while (accounts[from] < amount)
					con.await();
				System.out.printf("%10.2f from %d to %d\n", amount, from, to);
				accounts[from] -= amount;
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				accounts[to] += amount;
				System.out.printf("Total balance %10.2f\n", getTotalBalance());
				con.signalAll();
			} finally {
				lock.unlock();
			}
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
				try {
					bank.transfer(from, to, amount);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
