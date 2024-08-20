package thread.control.printer;

import static util.MyLogger.*;
import static util.ThreadUtils.*;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

/*
인터럽트를 실제 어떤 식으로 활용할 수 있는지 조금 더 실용적인 예제
사용자의 입력을 프린터에 출력하는 간단한 로직을 만들어 보자
여기서는 크게 사용자의 입력을 받는 `main` 스레드와 사용자의 입력을 출력하는 `printer` 스레드로 나누어진다.

1. 개선 포인트
- 사용자가 "q"를 입력해도 작업내부에 있는 sleep 때문에 바로 종료가되지 않는다.
- 인터럽트를 이용해서 개선해보자

 */
public class MyPrinterV1 {

	public static void main(String[] args) {
		Printer printer = new Printer();
		Thread thread = new Thread(printer, "printer");
		thread.start();

		Scanner userInput = new Scanner(System.in);
		while (true) {
			log("프린터할 문서를 입력하세요. 종료 (q): ");
			String input = userInput.nextLine();
			if (input.equals("q")) {
				printer.work = false;
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
			while (work) {
				if (jobQueue.isEmpty()) {
					continue;
				}

				String job = jobQueue.poll();
				log("출력 시작: " + job + ", 대기 문서: " + jobQueue);
				sleep(3000); //출력에 걸리는 시간
				log("출력 완료: " + job);
			}
			log("프린터 종료");
		}

		public void addJob(String input) {
			jobQueue.offer(input);
		}
	}
}
