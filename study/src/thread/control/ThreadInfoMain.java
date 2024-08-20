package thread.control;

import static util.MyLogger.*;

import thread.HelloRunnable;
import thread.HelloThread;
import util.MyLogger;

public class ThreadInfoMain {

	public static void main(String[] args) {
		Thread mainThread = Thread.currentThread();// 현재 쓰레드, 지금은 메인
		log("mainThread = " + mainThread); // 쓰레드 Id, 이름, 우선순위, 그룹이 출력됨
		log("mainThread.threadId() = " + mainThread.threadId()); // JVM이 Id를 임의로 부여함
		log("mainThread.getName() = " + mainThread.getName());
		log("mainThread.getPriority() = " + mainThread.getPriority()); // 1~10 기본값은 5
		log("mainThread.getThreadGroup() = " + mainThread.getThreadGroup());
		log("mainThread.getState() = " + mainThread.getState()); // 쓰레드의 상태 가장 중요

		Thread myThread = new Thread(new HelloRunnable(), "myThread");
		log("myThread = " + myThread);
		log("myThread.threadId() = " + myThread.threadId());
		log("myThread.getName() = " + myThread.getName());
		log("myThread.getPriority() = " + myThread.getPriority());
		log("myThread.getThreadGroup() = " + myThread.getThreadGroup());
		log("myThread.getState() = " + myThread.getState());
	}
}
