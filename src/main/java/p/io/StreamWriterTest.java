package p.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class StreamWriterTest {
	public static void main(String[] args) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os);
		osw.write("好");
		osw.flush();
	}
}
