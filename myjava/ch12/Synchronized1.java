package ch12;


//synchronized : 멀티스레드 환경에서 데이터의 일관성을 보장해주는 동기화 기능.(임계영역)
public class Synchronized1 implements Runnable{
    public synchronized void a (String who){
        try{
            Thread.sleep(200);
            System.out.println(who + "b() 호출");
            b(who);
        }catch(Exception e){}
    }
    public synchronized void b(String who){
        try{
            Thread.sleep(200);
            System.out.println(who + "a() 호출");
            a(who);
        }catch(Exception e){}
    }

    @Override
    public void run() {
        b(Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        Synchronized1 sz = new Synchronized1();
        Thread t1 = new Thread(sz,"첫번째");
        Thread t2 = new Thread(sz,"두번째");
        t1.start();
        t2.start();

    }
}
