package p.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {

	private static final int THREADS_CONUT = 2;
	public static AtomicInteger COUNT = new AtomicInteger(0);
//	public static int count = 0;

	public static void increase() {
		COUNT.incrementAndGet();
//		count++;
	}

	public static void main(String[] args) {
		Thread[] threads = new Thread[THREADS_CONUT];
		for (int i = 0; i < THREADS_CONUT; i++) {
			threads[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < 1000; i++) {
						increase();
					}
				}
			});
			threads[i].start();
		}

		while (Thread.activeCount() > 1) {
			Thread.yield();
		}
		System.out.println(COUNT.get());
	}
}
