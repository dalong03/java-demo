package p.ref;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ReferenceTest {

	@Test
	public void testStrongRef() {
		A a = new A();
		System.out.println(a);
		a = null;
		System.out.println("a");
	}

	@Test
	public void testSoftRef() {
		List<SoftReference<Object>> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			SoftReference<Object> ref = new SoftReference<>(new A());
			list.add(ref);
			System.out.println(ref);
			Object o = ref.get();
			System.out.println(o);
		}
	}

	@Test
	public void testSoftRef2() {
		ReferenceQueue<Object> qu = new ReferenceQueue<>();
		List<SoftReference<Object>> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			SoftReference<Object> ref = new SoftReference<>(new A(), qu);
			list.add(ref);
			System.out.println(ref);
			Object o = ref.get();
			System.out.println(o);
		}
	}

	@Test
	public void testWeakRef() {
		List<WeakReference<Object>> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			WeakReference<Object> ref = new WeakReference<>(new A());
			list.add(ref);
			System.out.println(ref);
			Object o = ref.get();
			System.out.println(o);
		}
	}

	@Test
	public void testWeakRef2() {
		ReferenceQueue<Object> qu = new ReferenceQueue<>();
		List<WeakReference<Object>> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			WeakReference<Object> ref = new WeakReference<>(new A(), qu);
			list.add(ref);
			System.out.println(ref);
			Object o = ref.get();
			System.out.println(o);
		}
	}

	static class A {
		byte[] bys = new byte[10 * 1024 * 1024];
	}

}
