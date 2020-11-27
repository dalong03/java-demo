package p.io;

import java.io.ByteArrayOutputStream;

public class ByteArrayTest {
	public static void main(String[] args) throws InterruptedException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		os.write(97);
	}
}
