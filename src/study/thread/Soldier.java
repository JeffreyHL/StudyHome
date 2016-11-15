package study.thread;

public class Soldier implements Runnable {

	private String name = "";

	public Soldier(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println(name + "执行任务 " + Commander.queue.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}