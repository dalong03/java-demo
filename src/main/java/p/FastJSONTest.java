package p;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class FastJSONTest {
	public static void main(String[] args) throws InterruptedException, IOException {

		Map<String, Object> map = new HashMap<>();
		map.put("name", "tom");
		map.put("age", 27);

		String jsonStr = JSON.toJSONString(map);
		System.out.println(jsonStr);

		String jsonStr2 = "{\"name\":\"tom\",\"age\":27}";
		JSONObject jsonObj = JSON.parseObject(jsonStr2);
		System.out.println(jsonObj);

		List<String> list = new ArrayList<>();
		list.add("tom");
		list.add("jerry");
		String jsonStr3 = JSON.toJSONString(list);
		System.out.println(jsonStr3);

		String jsonStr4 = "[\"tom\",\"jerry\"]";
		JSONArray jsonArray = JSON.parseArray(jsonStr4);
		System.out.println(jsonArray);
		List<String> jsonList = JSON.parseArray(jsonStr4, String.class);
		System.out.println(jsonList);

		List<Map<String, Object>> list2 = new ArrayList<>();
		Map<String, Object> map2 = new HashMap<>();
		map2.put("name", "tom");
		map2.put("age", 27);
		Map<String, Object> map3 = new HashMap<>();
		map3.put("name", "tom");
		map3.put("age", 27);
		list2.add(map2);
		list2.add(map3);
		String jsonStr5 = JSON.toJSONString(list2);
		System.out.println(jsonStr5);
		
		String jsonStr6 = "[{\"name\":\"tom\",\"age\":27},{\"name\":\"tom\",\"age\":27}]";
		JSONArray jsonArray2 = JSON.parseArray(jsonStr6);
		System.out.println(jsonArray2);
		List<Map> jsonArray3 = JSON.parseArray(jsonStr6, Map.class);
		System.out.println(jsonArray3);
	}

}