package p.memory;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class DirectMemoryTest {
	public static void main(String[] args) {
		List<ByteBuffer> list = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			ByteBuffer buf = ByteBuffer.allocateDirect(10 * 1000 * 1000);
			list.add(buf);
		}
		System.out.println("=");
	}
}
