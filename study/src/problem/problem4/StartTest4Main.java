package problem.problem4;

import util.MyLogger;

/*
`Thread-A` , `Thread-B` 두 스레드를 만들어라

`Thread-A` 는 1초에 한 번씩 "A"를 출력한다.

`Thread-B` 는 0.5초에 한 번씩 "B"를 출력한다.

이 프로그램은 강제 종료할 때 까지 계속 실행된다.

 */
public class StartTest4Main {

	public static void main(String[] args) {

		// Thread thread1 = new Thread(new ThreadA(), "Thread-A");
		// thread1.start();
		// Thread thread2 = new Thread(new ThreadB(), "Thread-B");
		// thread2.start();

		Thread threadA = new Thread(new PrintWork("A", 1000), "Thread-A");
		Thread threadB = new Thread(new PrintWork("B", 500), "Thread-B");

		threadA.start();
		threadB.start();

	}

	static class PrintWork implements Runnable {
		private String content;
		private int sleepMs;

		public PrintWork(String content, int sleepMs) {
			this.content = content;
			this.sleepMs = sleepMs;
		}

		@Override
		public void run() {
			MyLogger.log(content);
			try {
				Thread.sleep(sleepMs);
				run();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	static class ThreadA implements Runnable {

		@Override
		public void run() {
				try {
					Thread.sleep(1000);
					MyLogger.log("A");
					run();

				}catch (InterruptedException e) {
					new RuntimeException(e);
				}

		}
	}

	static class ThreadB implements Runnable {

		@Override
		public void run() {

			try {
				Thread.sleep(500);
				MyLogger.log("B");
				run();

			}catch (InterruptedException e) {
				new RuntimeException(e);
			}

		}
	}
}


