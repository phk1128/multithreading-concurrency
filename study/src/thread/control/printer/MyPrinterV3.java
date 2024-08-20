package thread.control.printer;

import static util.MyLogger.*;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

/*
반복문의 조건을 !Thread.interrupted() 로 바꿔서 인터럽트가 호출되면 반복문을 빠져나온다.
만약 반복문 내부에 try-catch 문이 실행중에 인터럽트가 걸렸다면, 인터럽트 예외가 발생되어 브레이크 될것이다.

1. 개선 포인트
- 반복문 자체가 조건을 확인하는것도 지속적으로 cpu를 사용하게 된다. yield를 이용해서 이를 개선해야한다.
 */
public class MyPrinterV3 {

	public static void main(String[] args) {
		Printer printer = new Printer();
		Thread printerThread = new Thread(printer, "printer");
		printerThread.start();
		Scanner userInput = new Scanner(System.in);
		while (true) {
			System.out.println("프린터할 문서를 입력하세요. 종료 (q): "); String input = userInput.nextLine();
			if (input.equals("q")) {
				printerThread.interrupt();
				break;
			}
			printer.addJob(input);
		}
	}

	static class Printer implements Runnable {
		volatile boolean work = true;
		/*
		1.Concurrent는 lock을 사용하지 않고 동시성을 보장
		2.CAS 알고리즘을 사용하여 스레드 간의 충돌을 해결
		1,2를 통해서 여러 스레드가 동시에 큐에 접근해도 성능 저하가 적으며, 스레드가 다른 스레드의 작업을 기다리지 않고 작업을 수행할 수 있다.
		 */
		Queue<String> jobQueue = new ConcurrentLinkedQueue<>();

		@Override
		public void run() {
			while (!Thread.interrupted()) {
				if (jobQueue.isEmpty()) {
					continue;
				}

				try {
					String job = jobQueue.poll();
					log("출력 시작: " + job + ", 대기 문서: " + jobQueue);
					Thread.sleep(3000); //출력에 걸리는 시간
					log("출력 완료: " + job);
				} catch (InterruptedException e) {
					log("인터럽트 발생");
					break;
				}
			}
			log("프린터 종료");
		}

		public void addJob(String input) {
			jobQueue.offer(input);
		}
	}
}
