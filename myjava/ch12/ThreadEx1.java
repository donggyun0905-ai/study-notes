package ch12;


//Thread(쓰레드): 하나의 프로그램(프로세스) 안에 세부적인 일의 단위
class Thread1 extends Thread{
    String name;

    public Thread1(String name){
        this.name = name;
    }

    //강제성은 아니지만 멀티 스레드 기능 위해서는 반드시 오버라이딩 해야함
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

public class ThreadEx1{
    public static void main(String[] args) {
        Thread1 t1 = new Thread1("리센느");
        Thread1 t2 = new Thread1("아이브");
        //t1.run(); //직접적인 효율은 멀티쓰레드 기능 안됨
        //t2.run();
        //JVM에 안에 쓰레드 스케줄러 존재.여기에 등록을 하는것이 START 이고
        //run 메소드 호출은 내부적으로 안에서 실행
        t1.start(); //스레드 스케줄러 등록
        t2.start();

    }
}
