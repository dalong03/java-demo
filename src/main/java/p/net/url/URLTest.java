package p.net.url;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class URLTest {
	public static void main(String[] args) throws IOException {
		URL url = new URL("http://localhost:8888/licensingservice/default");
		URLConnection con = url.openConnection();
		con.setConnectTimeout(120000);
		con.connect();
		try (InputStream is = con.getInputStream(); 
				InputStreamReader isr = new InputStreamReader(is)) {
			char[] ch = new char[4096];
			isr.read(ch);
			System.out.println(ch);
		}
	}
}
