package p;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonTest {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		Map<String, Object> m = om.readValue("{\"a\":1}", Map.class);
		System.out.println(m);
	}
}
