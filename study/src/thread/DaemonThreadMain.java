package thread;

public class DaemonThreadMain {

	public static void main(String[] args) {
		/*
		데몬스레드를 10초 동안 실행된다고 가정했을때, 메인 스레드가 다 종료되면 데몬스레드의 완료 또는 성공 여부 없이
		자바는 종료된다. 즉, 사용자 스레드가 종료되면 데몬 스레드는 자동으로 종료된다.
		 */
		System.out.println(Thread.currentThread().getName() + ": main() start");
		DaemonThread daemonThread = new DaemonThread();
		daemonThread.setDaemon(true); // 데몬 스레드로 설정하겠다. 이거 안하면 사용자 스레드로됨 그리고 false로 선언해도 사용자 스레드가 됨
		daemonThread.start();

		System.out.println(Thread.currentThread().getName() + ": main() end");
	}

	static class DaemonThread extends Thread {

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + ": run()");

			try {
				Thread.sleep(10000); // 10초 기다리게끔, 10초간 실행된다고 가상시뮬레이션
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

			System.out.println(Thread.currentThread().getName() +": run() end");
		}
	}
}
