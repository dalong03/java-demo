package p.io;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BufferedStreamTest {
	public static void main(String[] args) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(os);
		bos.write(97);
		bos.flush();
	}
}
