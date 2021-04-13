package p;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class Test {
	public static void main(String[] args) throws InterruptedException, IOException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		System.out.println(calendar.get(1));
		System.out.println(calendar.get(2) + 1);
		System.out.println(calendar.get(5));
	}
}
