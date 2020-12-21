package p.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

public class ExecutorTest {
	public static void main(String[] args) {
//		ExecutorService pool = Executors.newFixedThreadPool(1);
		ExecutorService pool = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(1),
				new AbortPolicy());
		pool.execute(new R1());
		pool.execute(new R1());
//		pool.execute(new R1());
	}

	static class R1 implements Runnable {

		@Override
		public void run() {
			System.out.println("run");
		}

	}
}
