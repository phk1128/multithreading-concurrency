package problem.problem2_3;

import util.MyLogger;

/*
2.
CounterRunnable` 이라는 이름의 클래스를 만들자, 이 클래스는 `Runnable` 인터페이스를 구현해야 한다.

`CounterRunnable` 은 1부터 5까지의 숫자를 1초 간격으로 출력해야 한다. 앞서 우리가 만든 `log()` 기능을 사용해서 출력해라.

`main` 메서드에서 `CounterRunnable` 의 인스턴스를 이용해서 `Thread` 를 생성하고 실행해라.

스레드의 이름은 "counter"로 지정해야 한다.

3.
방금 작성한 문제2를 익명 클래스로 구현해라.
 */
public class StartTest2Main {

	public static void main(String[] args) {

		CounterRunnable counterRunnable = new CounterRunnable();
		Thread thread1 = new Thread(counterRunnable, "counter1");
		thread1.start();

		// 익명 클래스 사용 방법
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= 5; i++) {
					try {
						Thread.sleep(1000);
						MyLogger.log("value: " + i);

					}catch (InterruptedException e) {
						new RuntimeException(e);
					}
				}
			}
		});

		thread2.setName("counter2");
		thread2.start();

		//람다 사용
		Thread thread3 = new Thread(() -> {
			for (int i = 1; i <= 5; i++) {
				try {
					Thread.sleep(1000);
					MyLogger.log("value: " + i);

				}catch (InterruptedException e) {
					new RuntimeException(e);
				}
			}
		});

		thread3.setName("counter3");
		thread3.start();

	}

	static class CounterRunnable implements Runnable {

		@Override
		public void run() {

			for (int i = 1; i <= 5; i++) {
				try {
					Thread.sleep(1000);
					MyLogger.log("value: " + i);

				}catch (InterruptedException e) {
					new RuntimeException(e);
				}
			}

		}
	}
}
