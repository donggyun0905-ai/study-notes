package ch12;

/*RunnableFrameEx4.java
1.9개 창의 크기가 200x200을 동시에 멀티비젼 만든다.
2.각각의 창에 랜덤한 색상과 랜던한 위치에 안이 채워진 원을 30개
만들고 크기는 10으로 지정한다. 그리고 sleep은 0.5초 지정한다.
3.반드시 동시에 실행이 되어 한다.*/

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class RunnableFrameEx4 extends MFrame implements Runnable{

    Color c;
    int x,y;
    Random r;

    public RunnableFrameEx4(Color c,int x, int y) {
        super(200,200,Color.WHITE);
        this.c = c;
        r = new Random();
        setLocation(x,y);//창뜨는 위치 값

    }

    @Override
    public void run() {
        try{
            for (int i = 0; i < 30; i++) {
                x = r.nextInt(200); //0~299난수
                y = r.nextInt(200); //0~299난수
                Thread.sleep(500);//0.5초
                repaint();//update 호출
            }
        }catch(Exception e){}
    }

    @Override//지정한 좌표만 새롭게 그리는 기능
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
        Random rand = new Random();
        for (int i = 0; i < 9; i++) {
            Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            int x = (i % 3) * 200;
            int y = (i / 3) * 200;
            RunnableFrameEx4 f = new RunnableFrameEx4(color, x, y);
            Thread t = new Thread(f);
            t.start();
        }
    }
}








