package p.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockConditionTest {
	static int num = 0;

	public static void main(String[] args) {
		Lock lock = new ReentrantLock();
		Condition con = lock.newCondition();
		try {
			lock.lock();
			if (num > 0)
				try {
					con.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			System.out.println(con);
			con.signalAll();
		} finally {
			lock.unlock();
		}
	}
	
	public void m1() {
		
	}
}
