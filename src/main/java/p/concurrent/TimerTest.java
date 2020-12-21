package p.concurrent;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {
	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.schedule(new TimerRun(), 2000, 5000);

//		Calendar c = Calendar.getInstance();
//		c.set(2020, 10, 28);
//		Date date = c.getTime();
//		System.out.println(date);
		timer.scheduleAtFixedRate(new TimerRun(), 3000, 5000);
	}

	static class TimerRun extends TimerTask {

		@Override
		public void run() {
			System.out.println("run");
		}

	}
}
