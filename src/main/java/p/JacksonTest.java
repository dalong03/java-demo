package p;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonTest {

	@SuppressWarnings("unchecked")
	@Test
	public void deserial() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		Map<String, Object> m = om.readValue("{\"a\":11, \"b\":{\"c\":\"22\"}}", Map.class);
		System.out.println(m);
	}

	@Test
	public void serial() throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> in = new HashMap<>();
		in.put("a", 1);
		Map<String, Object> subIn = new HashMap<String, Object>();
		subIn.put("c", "22");
		in.put("b", subIn);
		ObjectMapper om = new ObjectMapper();
		String out = om.writeValueAsString(in);
		System.out.println(out);
	}
}
