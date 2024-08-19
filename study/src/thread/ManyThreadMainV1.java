package thread;

import static util.MyLogger.*;

import util.MyLogger;

public class ManyThreadMainV1 {

	public static void main(String[] args) {

		log("main() start");

		/*
		 thread를 중복해서 start()할 수 없음 thread1.start(); thread1.start(); 안됨
		 이미 스레드가 생성되어 실행하고 있으므로 동일한 스레드를 다시 생성할 수 없음
		 */

		HelloRunnable runnable = new HelloRunnable();
		Thread thread1 = new Thread(runnable);
		thread1.start();
		Thread thread2 = new Thread(runnable);
		thread2.start();
		Thread thread3 = new Thread(runnable);
		thread3.start();


		log("main() end");
	}
}
