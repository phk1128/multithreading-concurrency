package thread.control.interrupt;

import static util.MyLogger.*;
import static util.ThreadUtils.*;

/*
작업의 반복문 조건을 인터럽트가 발생했을때 멈추게 끔 수정

1.메인스레드
- 100ms 뒤에 myTask 작업을 수행하는 스레드에 인터럽트 발생시킴

2.myTask 작업 스레드
- Thread.currentThread().isInterrupted() 로 현재 스레드의 인터럽트 상태를 체크한다.
- 메인 스레드가 인터럽트를 호출하여 반복문 조건에 의해 반복문이 끝남
- 인터럽트 상태는 계속 true
- 자원 정리를 시도 후 sleep을 만나 인터럽트 예외가 발생
- try 내부의 자원 정리 완료를 출력하지 못하고 catch로 넘어가게 됨

3. 개선 포인트
- 만약 자원 정리를 위해 일정 시간이 필요하다고 한다면 sleep 을 만나도 예외가 터지지 않게 해야함

 */

public class ThreadStopMainV3 {

	public static void main(String[] args) {

		MyTask myTask = new MyTask();
		Thread thread = new Thread(myTask, "work");
		thread.start();

		sleep(100);
		log("작업 중단 지시 - thread.interrupt()"); thread.interrupt();
		log("work 스레드 상태 = " + thread.getState());
		log("work 스레드 인터럽트 상태1 = " + thread.isInterrupted());

	}

	static class MyTask implements Runnable {
		volatile boolean runFlag = true; // 스레드는 volatile 키워드에 의해 항상 최신 값을 읽어오게 된다.

		@Override
		public void run() {
			// 인터럽트 상태는 계속 true
			while (!Thread.currentThread().isInterrupted()) {
				log("작업 중");
			}
			log("work 스레드 인터럽트 상태2 = " + Thread.currentThread().isInterrupted()); // 인터럽트 상태는 계속 true

			try {
				log("자원 정리 시도");
				Thread.sleep(1000); // 인터럽트 예외가 발생
				log("자원 정리 완료");

			} catch (InterruptedException e) {
				log("자원 정리 실패 - 자원 정리 중 인터럽트 발생");
				log("work 스레드 인터럽트 상태3 = " + Thread.currentThread().isInterrupted()); // 인터럽트 예외가 발생하여 false
			}
			log("작업 종료");

		}
	}
}
