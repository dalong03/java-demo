package p.gc;

import org.junit.Test;

public class GcRootsTest {
	@Test
	public void ref() {
		byte[] bys = new byte[20 * 1000 * 1000];
		System.out.println(bys);
		bys = null;
		System.out.println("==");
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void methodStaticPro() throws ClassNotFoundException {
		Class c = Class.forName("p.gc.GcRootsTest$A");
		c = null;
	}

	static class A {
		static byte[] bys = new byte[20 * 1000 * 1000];
	}
}
