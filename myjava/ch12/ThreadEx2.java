package ch12;


class NoThread2 extends Thread{
    String name;

    public NoThread2(String name){
        this.name = name;
    }

    public void run(){
        for (int i = 0; i < 10; i++) {
            System.out.println(i+" : " + name);
            try {
                Thread.sleep(200);//현재 쓰레드를 0.2 초 동안 일하지 말고 잠시 졸아라
            } catch (InterruptedException e) {
            }
        }
    }

    public void start(){
        run();
    }
}

public class ThreadEx2{
    public static void main(String[] args) {
        NoThread2 t1 = new NoThread2("블랙핑크");
        NoThread2 t2 = new NoThread2("에스파");
        t1.start();
        t2.start();
    }
}
