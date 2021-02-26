package p.concurrent;

public class DeadLockTest implements Runnable {

	private static Object obj1 = new Object();
	private static Object obj2 = new Object();
	private boolean flag;

	public DeadLockTest(boolean flag) {
		this.flag = flag;
	}

	public static void main(String[] args) {

		Thread t1 = new Thread(new DeadLockTest(true), "线程1");
		Thread t2 = new Thread(new DeadLockTest(false), "线程2");

		t1.start();
		t2.start();
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + "运行");

		if (flag) {
			synchronized (obj1) {
				System.out.println(Thread.currentThread().getName() + "已经锁住obj1");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (obj2) {
					// 执行不到这里
					System.out.println("1秒钟后，" + Thread.currentThread().getName() + "锁住obj2");
				}
			}
		} else {
			synchronized (obj2) {
				System.out.println(Thread.currentThread().getName() + "已经锁住obj2");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (obj1) {
					// 执行不到这里
					System.out.println("1秒钟后，" + Thread.currentThread().getName() + "锁住obj1");
				}
			}
		}
	}

}
