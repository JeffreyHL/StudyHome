package study._201705_before_.thread;

import java.util.concurrent.TimeUnit;

public class Commander implements Runnable {

	public static TaskQueue queue = new TaskQueue();

	public static void main(String[] args) {
		new Thread(new Commander()).start();
		for (int i = 0; i < 3; i++) {
			new Thread(new Soldier("士兵" + i)).start();
		}
	}

	@Override
	public void run() {
		for (int i = 0; i < 30; i++) {
			queue.put(String.valueOf(i));
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.exit(0);
	}
}