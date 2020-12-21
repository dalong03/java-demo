package p.concurrent;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ScheduledExecutorServiceTest {

	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		c.set(2020, 10, 28, 18, 25, 0);
		Date date = c.getTime();
		System.out.println(date);
//		long delay = (date.getTime() - System.currentTimeMillis()) / 1000;
		long delay = 2;
		System.out.println(delay);

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(new Runnable() {
			public void run() {
				System.out.println("beep");
			}
		}, delay, 10, SECONDS);
	}

}
