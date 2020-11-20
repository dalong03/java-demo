package p;

import java.io.IOException;

@ConcreteAnnotation
public class Test {

	public static void main(String[] args) throws IOException {
		System.out.println(49 >>> 16);
		System.out.println(49 ^ (49 >>> 16));
		System.out.println(new Object().hashCode());
		System.out.println(new Object().hashCode());
	}

}
