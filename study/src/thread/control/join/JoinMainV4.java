package thread.control.join;

import static util.MyLogger.*;
import static util.ThreadUtils.*;

import util.MyLogger;
import util.ThreadUtils;

/*
`main` 스레드가 `1 ~ 100` 으로 더하는 작업을 `thread-1` , `thread-2` 에 각각 작업을 나누어 지시하면 CPU 코어 를 더 효율적으로 활용할 수 있다. CPU 코어가 2개라면 이론적으로 연산 속도가 2배 빨라진다.
`thread-1` : `1 ~ 50` 까지 더하기
`thread-2` : `51 ~ 100` 까지 더하기
`main` : 두 스레드의 계산 결과를 받아서 합치기(이건 간단한 연산 한 번이니 속도 계산에서 제외하자)
 */
public class JoinMainV4 {

	public static void main(String[] args) throws InterruptedException {

		SumTask sumTask1 = new SumTask(1, 50);
		SumTask sumTask2 = new SumTask(51, 100);

		Thread thread1 = new Thread(sumTask1, "thread-1");
		Thread thread2 = new Thread(sumTask2, "thread-2");

		thread1.start(); // 스레드1 실행 시작
		thread2.start(); // 스레드2 실행 시작

		log("join() - main 스레드가 thread1, thread2가 종료될때까지 대기");
		thread1.join(); // 시간을 매개변수로 주면 해당 시간 만큼만 기다림
		thread2.join();
		log("thread1, thread2 완료");

		int sum = sumTask1.result + sumTask2.result;
		log("sumTask1 + sumTask2 = " + sum);
		log("End");

	}

	static class SumTask implements Runnable {

		int startValue;
		int endValue;
		int result = 0;

		public SumTask(int startValue, int endValue) {
			this.startValue = startValue;
			this.endValue = endValue;
		}

		@Override
		public void run() {
			log("작업 시작");
			sleep(2000); // 작업이 2초 걸린다고 가정
			int sum = 0;
			for (int i = startValue; i <= endValue; i++) {
				sum += i;
			}
			result = sum;
			log("작업 완료 result = " + result);
		}
	}
}
