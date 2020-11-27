package p.concurrent;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

public class DoubleLockTest {
	private Object o1 = new Object();
	private Integer o2 = new Integer(11);

	public static void main(String[] args) throws IOException {
		DoubleLockTest t = new DoubleLockTest();
		t.t1();
	}

	@Test
	public void test() {
		synchronized (o1) {
			t3();
			synchronized (o2) {
				t4();
			}
		}
	}

	@Test
	public void test2() {
		Lock l1 = new ReentrantLock();
		try {
			l1.lock();
			t3();
			Lock l2 = new ReentrantLock();
			try {
				l2.lock();
				t4();
			} finally {
				l2.unlock();
			}
		} finally {
			l1.unlock();
		}
	}

	public synchronized void t1() {
		System.out.println("t1");
		t2();
		Class2 c2 = new Class2();
		c2.c1();
	}

	public synchronized void t2() {
		System.out.println("t2");
	}

	public void t3() {
		System.out.println("t3");
	}

	public void t4() {
		System.out.println("t4");
	}

	static class Class2 {
		public synchronized void c1() {
			System.out.println("c1");
		}

		public void c2() {
			System.out.println("c2");
		}
	}

}
