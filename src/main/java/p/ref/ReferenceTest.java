package p.ref;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

import org.junit.Test;

public class ReferenceTest {

	@Test
	public void testStrongRef() {
		Object o = new Object();
		System.out.println(o);
		System.gc();
		System.out.println(o);
	}

	@Test
	public void testSoftRef() {
		SoftReference<Object> ref = new SoftReference<>(new Object());
		System.out.println(ref);
		System.out.println(ref.get());
		System.gc();
		System.out.println(ref);
		System.out.println(ref.get());

	}
	
	@Test
	public void testSoftRef2() {
		ReferenceQueue<Object> qu = new ReferenceQueue<>();
		SoftReference<Object> ref = new SoftReference<>(new Object(), qu);
		System.out.println(ref);
		System.out.println(ref.get());
		System.out.println(qu.poll());
		System.gc();
		System.out.println(ref);
		System.out.println(ref.get());
		System.out.println(qu.poll());
		
	}

	@Test
	public void testWeakRef() {
		WeakReference<Object> ref = new WeakReference<>(new Object());
		System.out.println(ref);
		System.out.println(ref.get());
		System.gc();
		System.out.println(ref);
		System.out.println(ref.get());
	}
	
	@Test
	public void testWeakRef2() {
		ReferenceQueue<Object> qu = new ReferenceQueue<>();
		WeakReference<Object> ref = new WeakReference<>(new Object(), qu);
		System.out.println(ref);
		System.out.println(ref.get());
		System.out.println(qu.poll());
		System.gc();
		System.out.println(ref);
		System.out.println(ref.get());
		System.out.println(qu.poll());
		
	}

}
