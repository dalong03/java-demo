package p.url;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class URLTest {
	public static void main(String[] args) throws IOException {
		URL url = new URL("http://localhost:8888/licensingservice/default");
		URLConnection con = url.openConnection();
		con.setConnectTimeout(120000);
		con.connect();
		try (InputStream is = con.getInputStream(); Scanner scanner = new Scanner(is);) {
			while (scanner.hasNextLine()) {
				System.out.println(scanner.nextLine());
			}
		}
	}
}
