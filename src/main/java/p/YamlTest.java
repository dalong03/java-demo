package p;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.yaml.snakeyaml.Yaml;

public class YamlTest {
	public static void main(String[] args) throws IOException {
		Yaml yaml = new Yaml();
		InputStream inputStream = new ClassPathResource("log4j2.yml").getInputStream();
		Map<String, Object> obj = yaml.load(inputStream);
		System.out.println(obj);
	}
}
