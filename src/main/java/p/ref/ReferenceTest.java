package p.ref;

import java.lang.ref.SoftReference;

import org.junit.Test;

public class ReferenceTest {

	@Test
	public void testStrongRef() {
		Object o = new Object();
		Object o2 = o;
		o = null;
		System.gc();
		System.out.println(o2);
	}

	@Test
	public void testSoftRef() {
		Object o = new Object();
		SoftReference<Object> ref = new SoftReference<>(o);
		System.gc();
		System.out.println(ref.get());
	}
}
