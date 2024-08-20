package thread.control.join.problem1;

import static util.MyLogger.*;
import static util.ThreadUtils.*;

/*
코드를 실행하기 전에 로그가 어떻게 출력될지, 총 실행 시간이 얼마나 걸릴지 예측해 보자
 */

/*
t1 1~3 전부 출력 3초 소요
t2 1~3 전부 출력 3초 소요
t3 1~3 전부 출력 3초 소요
join()에 의해서 이렇게 t1 부터 순차적으로 출력되고, 총 9초가 소요된다.
 */
public class JoinTest1Main {

	public static void main(String[] args) throws InterruptedException {

		Thread t1 = new Thread(new MyTask(), "t1");
		Thread t2 = new Thread(new MyTask(), "t2");
		Thread t3 = new Thread(new MyTask(), "t3");

		t1.start();
		t1.join();
		t2.start();
		t2.join();
		t3.start();
		t3.join();
		System.out.println("모든 스레드 실행 완료");
	}

	static class MyTask implements Runnable {

		@Override
		public void run() {
			for (int i = 1; i <= 3; i++) {
				log(i);
				sleep(1000);
			}
		}
	}
}
