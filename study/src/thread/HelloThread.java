package thread;

public class HelloThread extends Thread {

	@Override
	public void run() {
		// 현재 실행중인 쓰레드 이름을 출력
		System.out.println(Thread.currentThread().getName() + ": run()");
	}
}
