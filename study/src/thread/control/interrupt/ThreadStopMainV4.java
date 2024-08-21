package thread.control.interrupt;

import static util.MyLogger.*;
import static util.ThreadUtils.*;


/*
Thread.interrupted() : 인터럽트 상태가 true이면 true를 반환하고 false로 바꿔줌 즉, 인터럽트를 초기 상태로

반복문에 Thread.interrupted() 사용함으로써 인터럽트 상태를 false로 되돌려놓음
따라서 sleep을 만나도 인터럽트 예외가 발생하지 않고 try문이 끝까지 실해됨
 */
public class ThreadStopMainV4 {

	public static void main(String[] args) {

		MyTask task = new MyTask();
		Thread thread = new Thread(task, "work");
		thread.start();
		sleep(100); //시간을 줄임
		log("작업 중단 지시 - thread.interrupt()"); thread.interrupt();
		log("work 스레드 인터럽트 상태1 = " + thread.isInterrupted());

	}

	static class MyTask implements Runnable {
		@Override
		public void run() {
			// 인터럽트 상태가 true이면 true를 반환하고 인터럽트 상태를 false로 바꿔줌
			while (!Thread.interrupted()) {
				log("작업 중");
			}
			log("work 스레드 인터럽트 상태2 = " + Thread.currentThread().isInterrupted()); // 인터럽트 상태는 false

			try {
				log("자원 정리 시도");
				Thread.sleep(1000); // 인터럽트 상태가 Thread.interrupted()의해 false가 됐으므로 예외 발생 X
				log("자원 정리 완료");

			} catch (InterruptedException e) {
				log("자원 정리 실패 - 자원 정리 중 인터럽트 발생");
				log("work 스레드 인터럽트 상태3 = " + Thread.currentThread().isInterrupted()); // 인터럽트 예외가 발생하여 false
			}
			log("작업 종료");

		}
	}
}
