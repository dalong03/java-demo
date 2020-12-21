package p.sql;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class MySqlCliDemo {
	public static void main(String[] args) throws UnknownHostException, IOException {
		try (Socket socket = new Socket("localhost", 3306);) {
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();

			byte[] bys = new byte[2048];
			is.read(bys);
			System.out.println(new String(bys));

		}

	}

}
