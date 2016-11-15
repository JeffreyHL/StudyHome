package study.thread;

import java.util.LinkedList;

public class TaskQueue {

	private LinkedList<String> list = new LinkedList<String>();

	public synchronized String get() throws InterruptedException {
		while (list.isEmpty()) {
			wait();
		}
		return list.remove();
	}

	public synchronized void put(String obj) {
		if (obj == null || obj.equals(""))
			return;
		if (!list.contains(obj)) {
			list.add(obj);
			System.out.println("有新任务来了啦, 大家来取吧");
			notifyAll();
		}

	}
}