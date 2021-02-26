package p.redis;

import redis.clients.jedis.Jedis;

public class JedisDemo {
	public static void main(String[] args) {
		try (Jedis j = new Jedis("localhost", 6379)) {
			String reply = j.set("name2", "yu");
			System.out.println(reply);
			System.out.println(j.get("name2"));

		}
	}
}
