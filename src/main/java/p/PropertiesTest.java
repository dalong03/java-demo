package p;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;

public class PropertiesTest {
	public static void main(String[] args) throws InterruptedException, IOException {
		Properties ps = new Properties();
		InputStream is = new ClassPathResource("application.properties").getInputStream();
		ps.load(is);
		System.out.println(ps);
	}
}
