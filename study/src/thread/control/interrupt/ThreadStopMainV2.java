package thread.control.interrupt;

import static util.MyLogger.*;
import static util.ThreadUtils.*;


/*

interrupt()를 이용해서 메인 스레드에서 작업 종료를 요청하면 더 빨리 종료가 가능해진다.
스레드에 interrupt()을 호출 하면 WAITING, TIMED_WAITING, BLOCK 상태에 있던 해당 스레드가 깨어나고 인터럽트 예외를 발생시킨다.

1. 메인 스레드
- myTask 작업을 실행하는 스레드를 만들어서 시작한다.
- 4초 뒤에 해당 스레드에 인터럽트를 호출한다.

2. myTask를 실행하는 스레드
- runFlag 가 true 라면 3초가 걸리는 작업을 계속 실행한다.
- 인터럽트 예외가 터지면 인터럽트 상태, 메시지, 현재 스레드의 상태의 로그를 출력한다.
- 자원을 정리한다.

3. 개선 포인트
- 해당 로직의 경우 interrupt()호출 된 스레드는 sleep을 만나야지 TIMED_WAITING상태가 되어 인터럽트 예외가 발생하게 된다.
- 이를 기다리지 않고 바로 작업을 종료하게끔 개선이 필요하다.

 */
public class ThreadStopMainV2 {

	public static void main(String[] args) {

		MyTask myTask = new MyTask();
		Thread thread = new Thread(myTask, "work");
		thread.start();

		sleep(4000);
		log("작업 중단 지시 thread.interrupt()");
		thread.interrupt();
		log("work 스레드 인터럽트 상태1 = " + thread.isInterrupted()); // 인터럽트가 발생했으므로 true

	}

	static class MyTask implements Runnable {
		volatile boolean runFlag = true; // 스레드는 volatile 키워드에 의해 항상 최신 값을 읽어오게 된다.

		@Override
		public void run() {

			try {
				while (runFlag) {
					log("작업 중");
					/*
					TIMED_WAITING로 들어간다. 하지만 4초 뒤 메인 스레드에서 해당 스레드의 인터럽트를 호출하여 강제로 깨운다.
					이때 인터럽트 예외가 발생하게 된다.
					 */
					Thread.sleep(3000);
				}

			} catch (InterruptedException e) {
				/*
				자바는 인터럽트 예외가 발생하면 인터럽트 상태를 다시 초기값인 false로 되돌려 놓는다.
				 */
				log("work 스레드 인터럽트 상태2 = " + Thread.currentThread().isInterrupted());
				log("interrupt message = " + e.getMessage());
				log("state = " + Thread.currentThread().getState()); // RUNNABLE
			}
			log("자원 정리");
			log("작업 종료");

		}
	}
}
