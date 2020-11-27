package p;

import org.junit.Test;

public class CopyTest {
	public static void main(String[] args) throws CloneNotSupportedException {
		Person xl = new Person(new String("xl"), 21, new Person("dl"));
		Person xh = xl.clone();
		System.out.println(xl.getName() == xh.getName());
		System.out.println(xl.getFather() == xh.getFather());
		xh.setName("xh");
		xh.getFather().setName("dh");
		System.out.println(xl);
		System.out.println(xh);
	}

	@Test
	public void testParamTransport() throws CloneNotSupportedException {
		Person xl = new Person("xl");
		Person xh = paramTransport(xl);
		System.out.println(xl);
		System.out.println(xh);
	}

	public Person paramTransport(Person one) throws CloneNotSupportedException {
		one.setName("xh");
		return one;
	}

	@Test
	public void arrayCopy() {
		Person[] ps = { new Person("xl") };
		Person[] psCopy = ps.clone();
		System.out.println(ps == psCopy);
		System.out.println(ps[0] == psCopy[0]);
	}

	@Test
	public void arrayCopy2() {
		String[] ps = { new String("xl") };
		String[] psCopy = ps.clone();
		System.out.println(ps == psCopy);
		System.out.println(ps[0] == psCopy[0]);

		psCopy[0] = "xh";
		System.out.println(ps[0] + " " + psCopy[0]);
	}

	@Test
	public void arrayCopy3() {
		String[] ps = { new String("xl") };
		String[] psCopy = new String[10];
		System.arraycopy(ps, 0, psCopy, 0, ps.length);
		System.out.println(ps[0] == psCopy[0]);
	}
}
