package p.concurrent;

import java.io.IOException;

public class WaitNofityTest {
	volatile int num = 0;
	Object lock = new Object();

	public static void main(String[] args) throws InterruptedException, IOException {
//		WaitNofityTest t1 = new WaitNofityTest();
		WaitNofityTest t2 = new WaitNofityTest();
		WaitNofityTest t3 = new WaitNofityTest();
//		R1 r1 = new R1(t1);
		R2 r2 = new R2(t2);
		R3 r3 = new R3(t3);
//		new Thread(r1).start();
		new Thread(r2).start();
		new Thread(r3).start();
	}

	public synchronized void m1() {
		while (num++ % 2 == 0)
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		System.out.println(1);
		notifyAll();
	}

	public synchronized void m2() {
		while (num >= 0)
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		System.out.println("m2");
		notifyAll();
	}

	public void m3() {
		synchronized (lock) {
//			while (num >= 0)
//				try {
//					wait();
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
			System.out.println("m3");
			lock.notifyAll();
		}
	}

	static class R1 implements Runnable {
		WaitNofityTest t;

		R1(WaitNofityTest t) {
			this.t = t;
		}

		@Override
		public void run() {
			t.m1();
		}

	}

	static class R2 implements Runnable {
		WaitNofityTest t;

		R2(WaitNofityTest t) {
			this.t = t;
		}

		@Override
		public void run() {
			t.m2();
		}

	}
	
	static class R3 implements Runnable {
		WaitNofityTest t;
		
		R3(WaitNofityTest t) {
			this.t = t;
		}
		
		@Override
		public void run() {
			t.m3();
		}
		
	}
}
