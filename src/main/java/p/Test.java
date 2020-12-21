package p;

import java.io.IOException;
import java.nio.channels.Selector;

public class Test {
	public static void main(String[] args) throws InterruptedException, IOException {
		Selector s1 = Selector.open();
		Selector s2 = Selector.open();
		System.out.println(s1 == s2);
	}
}
