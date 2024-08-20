package thread.control.interrupt;

import static util.MyLogger.*;
import static util.ThreadUtils.*;


/*
1.작업의 runFlag를 메인 쓰레드에서 바꾼다.
2.작업 종료를 요청했지만, 반복문안에 sleep에 의해서 자원정리가 늦어진다.
3.이를 개선할 필요가 있다.
 */

public class ThreadStopMainV1 {

	public static void main(String[] args) {

		MyTask myTask = new MyTask();
		Thread thread = new Thread(myTask, "work");
		thread.start();

		sleep(4000);
		log("작업 중단 지시 runFlag = false");
		myTask.runFlag = false;

	}

	static class MyTask implements Runnable {
		volatile boolean runFlag = true; // 스레드는 volatile 키워드에 의해 항상 최신 값을 읽어오게 된다.

		@Override
		public void run() {
			while (runFlag) {
				log("작업 중");
				sleep(3000);
			}
			log("자원 정리");
			log("작업 종료");

		}
	}
}
