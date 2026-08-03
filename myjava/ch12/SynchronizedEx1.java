package ch12;


//Synchronized: 멀티 스레스 환경에서 데이터의 일관성을 보장해 주는 동기화 기능. (임계영역)
public class SynchronizedEx1  implements Runnable{

	
	public synchronized  void a(String who) {
		try {
			Thread.sleep(200);
			System.out.println(who+ "b호출");
			b(who);
			
		
		}catch(Exception e) {}
	}
	
	public  synchronized  void b(String who) {
		try {
			Thread.sleep(200);
			System.out.println(who+"b호출");
			b(who);
		}catch(Exception e) {}
	}
	
@Override{
	pubilc void run() {
		b(Thread.currentThread().getName());
	}
	}
	
	public static void main(String[] args) {
		
	}
}
