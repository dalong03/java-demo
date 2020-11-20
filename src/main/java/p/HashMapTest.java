package p;

import java.util.HashMap;

public class HashMapTest {

	public static void main(String[] args) {
		HashMap<Object, Object> m = new HashMap<>();
		m.put("1", new Object());
		m.put("2", new Object());
		m.put("2", new Object());
		
		m.put(new A(), new Object());
		m.put(new A(), new Object());
		
		System.out.println(m);
	}

	private static class A {

		@Override
		public int hashCode() {
			return 1;
		}

		@Override
		public boolean equals(Object obj) {
			return super.equals(obj);
		}

	}

}
