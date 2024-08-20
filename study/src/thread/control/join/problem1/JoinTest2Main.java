package thread.control.join.problem1;

import static util.MyLogger.*;
import static util.ThreadUtils.*;

/*
문제1의 코드를 변경해서 전체 실행 시간을 3초로 앞당겨보자.
 */

/*
t1~t3 start()를 전부 실행하고, 그다음에 t1~t3의 join()을 실행하면 된다.
start()가 호출된 순간부터 바로 작업이 실행되므로, 스레드3개가 병렬적으로 번갈아가면서 실행된다.
사용자가 보기에는 동시에 병렬적으로 실행되는것처럼 보임
 */
public class JoinTest2Main {

	public static void main(String[] args) throws InterruptedException {

		Thread t1 = new Thread(new JoinTest1Main.MyTask(), "t1");
		Thread t2 = new Thread(new JoinTest1Main.MyTask(), "t2");
		Thread t3 = new Thread(new JoinTest1Main.MyTask(), "t3");

		t1.start();
		t2.start();
		t3.start();

		t1.join();
		t2.join();
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
