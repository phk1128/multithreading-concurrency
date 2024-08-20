package thread.control.printer;

import static util.MyLogger.*;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

/*
Thread.yield()를 통해 해당 스레드의 cpu 사용을 다른 스레드에 양보할 수 있다.
양보를 하게 되면 RUNNABLE상태를 계속 유지된다. 정확히 말하면 cpu 대기큐에 들어가서 다시 기다리는것이다.
cpu 스케쥴러를 강제적으로 제어하는것이 아니라 cpu에게 자신은 아직 cpu가 필요하지 않으니 양보하겠다고 힌트를 주는것이다.

1. 큐가 비어져 있을때
Thread.yield(); 를 실행하여 해당 스레드의 반복문에서 지속적으로 cpu를 소모하는것을 방지할 수 있다.
사용자가 느끼기는 거의 불가능하지만 이를 통해 cpu 자원을 좀 더 효율적으로 사용할 수 있게 된다.
 */
public class MyPrinterV4 {

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
					Thread.yield(); // 큐가 비어있으면 해당 스레드를 양보한다.
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
