package ch12;

/* RunnableFrameEx3.java
1.창의 크기는 500x500 설정
2.생성자의 매개변수에 원 or 사각형을
받아서 채워지지 않은 도형을 10000개를
랜덤한 위치 및 색상으로 만든다.(sleep=2)
3.원과 사각형의 크기도 랜덤하게 지정(10~40)
4.반드시 동시에 만들어야 한다.
5.원그리기 : drawOval, 사각형그리기 : drawRect
*/

import java.awt.*;
import java.util.Random;

public class RunnableFrameEx3 extends MFrame implements Runnable{

    final static int SIZE = 10000;
    final static int WEIGHT = 500;
    final static int HEIGHT = 500;
    final static int OVAL = 0;
    final static int RECT = 1;
    int x, y, w, h;
    Random r = new Random();
    int shape;

    public RunnableFrameEx3(int shape, int x1, int y2) {
        super(WEIGHT,HEIGHT, Color.WHITE);
        this.shape = shape; //원 or 사각형
        setTitle(shape==OVAL?"원그리기" : "사각형그리기");
        setLocation(x1,y2);
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < SIZE; i++) {
                x = r.nextInt(500);
                y = r.nextInt(500);
                w = r.nextInt(10,40);
                h = r.nextInt(10,40);
                Thread.sleep(2);
                repaint();
            }
        } catch (InterruptedException e) {}
    }

    @Override
    public void update(Graphics g){
        g.clearRect(x,y,w,h);
        paint(g);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.setColor(new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256)));
        if(shape == OVAL){
            g.drawOval(x,y,w,h);
        }else{
            g.drawRect(x,y,w,h);
        }
    }

    public static void main(String[] args) {
        RunnableFrameEx3 f1 = new RunnableFrameEx3(OVAL,100,100);
        RunnableFrameEx3 f2 = new RunnableFrameEx3(RECT,400,100);
        Thread t1 =new Thread(f1);
        Thread t2 =new Thread(f2);
        t1.start();
        t2.start();
    }
}
;