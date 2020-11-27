package p.io;

import java.nio.ByteBuffer;

public class ByteBufferTest {
	public static void main(String[] args) {
		ByteBuffer buf = ByteBuffer.allocate(32);
		buf.put((byte) 97);
	}
}
