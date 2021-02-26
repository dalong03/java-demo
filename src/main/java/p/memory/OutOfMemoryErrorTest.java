package p.memory;
import org.junit.Test;

public class OutOfMemoryErrorTest {
	
	@Test
	public void t1() throws InterruptedException {
//		Thread.sleep(10000);
		System.out.println("=");
		byte[][] arr = new byte[10][];
		for (int i = 0; i < 10; i++) {
			arr[i] = new byte[10 * 1000 * 1000];
//			Thread.sleep(5000);
		}
		System.out.println("=");
		Thread.sleep(50000);
	}
	
	@Test
	public void t2() throws InterruptedException, ClassNotFoundException {
		System.out.println("==");
		Class.forName("p.Demo");
		System.out.println("==");
	}
}
