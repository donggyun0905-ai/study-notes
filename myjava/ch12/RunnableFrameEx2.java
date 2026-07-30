package ch12;

import java.awt.*;
import java.util.Random;

//구역주석 : 구역 지정하고 ctrl + shift + /  <- 주석처리(해제)
//커서가 있는 주석은 : ctrl + / <- 주석 해제도 마찬가지
public class RunnableFrameEx2 extends MFrame /*implements Runnable*/{

    Color c;
    int x,y;
    Random r;

    public RunnableFrameEx2(Color c, int x, int y) {
        super(300,300, Color.WHITE);
        this.c = c;
        r = new Random();
        setLocation(x,y);//창뜨는 위치 값
    }

    public void run(){
        try{
            for (int i = 0; i < 20; i++) {
                x = r.nextInt(300);
                y = r.nextInt(300);
                Thread.sleep(500);
                repaint();//update 호출
            }
        }catch(Exception e){}
    }

    public void start(){run();}

    @Override
    public void update(Graphics g){
        g.clearRect(x,y,10,10);
        paint(g);
    }

    @Override
    public void paint(Graphics g /*붓*/){
        super.paint(g);
        g.setColor(c); //붓에 매개변수를 들어온 색상을 세팅
        g.fillOval(x,y,10,10); //채우기 원
    }

    public static void main(String[] args) {
        RunnableFrameEx2 f1 = new RunnableFrameEx2(Color.BLUE,100,100);
        RunnableFrameEx2 f2 = new RunnableFrameEx2(Color.RED,400,100);
        f1.start();
        f2.start();
    }

}
