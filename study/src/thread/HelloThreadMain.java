package thread;

public class HelloThreadMain {

	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getName() + ": main() start");

		HelloThread helloThread = new HelloThread();
		System.out.println(Thread.currentThread().getName() + ": start() 호출 전");
		//run()을 실행하면 안됨, start()를 실행해야 helloThread 내부의 run()이 실행됨
		helloThread.start();
		System.out.println(Thread.currentThread().getName() + ": start() 호출 후");

		System.out.println(Thread.currentThread().getName() + ": main() end");

		System.out.println(Thread.currentThread().getName() + ": run() 호출 전");

		helloThread.run();

		System.out.println(Thread.currentThread().getName() + ": run() 호출 후");
	}
}
