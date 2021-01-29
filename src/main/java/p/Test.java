package p;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
	public static void main(String[] args) throws InterruptedException, IOException {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String date1 = sdf.format(date);
		System.out.println(date1);
	}
}
