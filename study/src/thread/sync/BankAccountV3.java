package thread.sync;

import static util.MyLogger.*;
import static util.ThreadUtils.*;

public class BankAccountV3 implements BankAccount {

	private int balance;

	public BankAccountV3(int balance) {
		this.balance = balance;
	}

	@Override
	public boolean withdraw(int amount) {

		log("거래 시작: " + getClass().getSimpleName());

		synchronized (this) {
			log("[검증 시작] 출금액 " + amount + ", 잔액: " + balance);
			if (balance < amount) {
				log("[검증 실패] 출금액: " + amount + ", 잔액: " + balance);
				return false;
			}
			log("[검증 완료] 출금액: " + amount + ", 잔액: " + balance);
			sleep(1000);
			balance -= amount;
			log("[출금 완료] 출금액: " + amount + ", 변경 잔액: " + balance);
			log("거래 종료");
			return true;
		}
	}

	@Override
	public synchronized int getBalance() {
		return balance;
	}
}
