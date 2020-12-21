package p.concurrent;

public class DoubleCheckedLock {
	private static volatile DoubleCheckedLock instance;

	private DoubleCheckedLock() {
	}

	public static DoubleCheckedLock getInstance() {
		if (instance == null) {
			synchronized (DoubleCheckedLock.class) {
//				if (instance == null) {
					instance = new DoubleCheckedLock();
//				}
			}
		}
		return instance;
	}

	public static void main(String[] args) {
		R1 r1 = new R1();
		new Thread(r1).start();
		new Thread(r1).start();
		new Thread(r1).start();
		new Thread(r1).start();
		new Thread(r1).start();
		new Thread(r1).start();
	}

	static class R1 implements Runnable {

		@Override
		public void run() {
			DoubleCheckedLock d = DoubleCheckedLock.getInstance();
			System.out.println(d);
		}

	}
}
