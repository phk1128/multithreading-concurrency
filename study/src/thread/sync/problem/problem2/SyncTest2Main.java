package thread.sync.problem.problem2;

import static util.MyLogger.*;

/*
다음 코드에서 `MyTask` 의 `run()` 메서드는 두 스레드에서 동시에 실행한다.
다음 코드의 실행 결과를 예측해보자.
그리고 `localValue` 지역 변수에 동시성 문제가 발생하는지 하지 않는지 생각해보자.

동시성 문제가 발생하지 않는다. 스레드1, 스레드2가 같은 주소의 인터스턴스를 참조하고 있으나,
localValue는 count() 메서드 내부의 지역변수로 사용하고 있기 때문에 스레드 끼리 공유하지 않는다.
 */
public class SyncTest2Main {

	public static void main(String[] args) {

		MyCounter myCounter = new MyCounter();

		Runnable task = new Runnable() {
			@Override
			public void run() {
				myCounter.count();
			}
		};

		Thread thread1 = new Thread(task, "Thread-1");
		Thread thread2 = new Thread(task, "Thread-2");
		thread1.start();
		thread2.start();


	}

	static class MyCounter {

		public void count() {
			int localValue = 0;

			for (int i = 0; i < 1000; i++) {
				localValue++;
			}

			log("결과: " + localValue);
		}
	}

}
