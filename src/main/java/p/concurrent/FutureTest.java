package p.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		FutureTask<Integer> future = new FutureTask<Integer>(new Task1());
		new Thread(future).start();
		while (!future.isDone()) {
			Thread.sleep(1000);
		}
		future.get();
	}

	static class Task1 implements Callable<Integer> {
		@Override
		public Integer call() throws Exception {
			Thread.sleep(2000);
			return 1;
		}
	}
}
