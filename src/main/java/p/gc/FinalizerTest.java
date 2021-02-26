package p.gc;

public class FinalizerTest {
	public static void main(String[] args) {
		Object o = new Object();
		System.out.println(o);
		o = null;
		System.out.println("=");
	}
}
