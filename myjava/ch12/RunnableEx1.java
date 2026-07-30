package ch12;

class Runnable1 implements Runnable{
    String name;

    public Runnable1(String name) {
        this.name = name;
    }

    @Override
    public void run(){
        for (int i = 0; i < 10; i++) {
            System.out.println(i+" : " + name);
            try {
                Thread.sleep(200);//현재 쓰레드를 0.2 초 동안 일하지 말고 잠시 졸아라
            } catch (InterruptedException e) {
            }
        }
    }
}

public class RunnableEx1 {
    public static void main(String[] args) {
        Runnable1 r1 = new Runnable1("BTS");
        Runnable1 r2 = new Runnable1("The Rose");
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();

    }
}
