package p.redis;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class RedisCliDemo {
	public static void main(String[] args) throws UnknownHostException, IOException {
		try (Socket socket = new Socket("localhost", 6378);) {
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();

			String content = set();
			os.write(content.getBytes());
			os.flush();

			byte[] bys = new byte[2048];
			is.read(bys);
			System.out.println(new String(bys));

		}

	}

	static String set() {
		String content = "name";
		StringBuilder sb = new StringBuilder();
		sb.append("*3").append("\r\n");
		sb.append("$3").append("\r\n");
		sb.append("SET").append("\r\n");
		sb.append("$").append(content.length()).append("\r\n");
		sb.append(content).append("\r\n");
		sb.append("$2").append("\r\n");
		sb.append("yu").append("\r\n");
		return sb.toString();
	}

	static String get() {
		String content = "name";
		StringBuilder sb = new StringBuilder();
		sb.append("*2").append("\r\n");
		sb.append("$3").append("\r\n");
		sb.append("GET").append("\r\n");
		sb.append("$").append(content.length()).append("\r\n");
		sb.append(content).append("\r\n");
		return sb.toString();
	}
}
