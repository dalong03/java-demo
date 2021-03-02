package p.gc;

public class FinalizerTest {
	
	static A a;
	public static void main(String[] args) {
		A a = new A();
		System.out.println(a);
		a = null;
		System.out.println("==");
	}

	static class A {
		byte[] bys = new byte[20 * 1024 * 1024];
		public void finalize() throws Throwable { 
			FinalizerTest.a = this;
		}
	}
}
