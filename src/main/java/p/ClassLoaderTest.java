package p;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import org.junit.Test;

public class ClassLoaderTest {

	@Test
	public void loadResource() {
		ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
		URL url = classLoader.getResource("org/apache/http/auth/AUTH.class");
		System.out.println(url);
	}

	@Test
	public void loadResources() throws IOException {
		ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
		Enumeration<URL> urls = classLoader.getResources("org/apache/http/auth/AUTH.class");
		while (urls.hasMoreElements()) {
			System.out.println(urls.nextElement());
		}
	}
}
