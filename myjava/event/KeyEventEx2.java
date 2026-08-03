package event;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

public class KeyEventEx2 extends MFrame{

    Button move;

    public KeyEventEx2() {
        super(400,500,new Color(100,200,100));
        setLayout(null);
        move = new Button("move");
        move.setBackground(MColor.rColor());
        move.setBounds(200,250,50,30);
        /*Adapter는 Listener를 구현한 추상클래스이고 필요한 메소드만 오버라이딩 할 수 있도록 제공되는 클래스
        * ActionListener이랑 ItemListener은 Adapter 클래스 없음 <- 메소드 한개 밖에 없기 때문
        * 익명 클래스를 만들 수 있는 조건은 추상 클래스 또는 인터페이스
        * */
        move.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                int code = e.getKeyCode();
                //System.out.println("code: " + code);
                String txt = KeyEvent.getKeyText(code);
                int x = move.getX();//현재 버튼의 x 좌표
                int y = move.getY();//현재 버튼의 y 좌표
                if(txt.equals("Up")){
                    y -= 10;
                }else if(txt.equals("Down")){
                    y += 10;
                }else if(txt.equals("Left")){
                    x -= 10;
                }else if(txt.equals("Right")){
                    x += 10;
                }
                if(x>0 && x<getWidth()-50&&y>20&&y<getHeight()-30){
                    move.setLocation(x,y);
                }
            }
        });
        add(move);
        move.requestFocus();
        validate();
    }

    public static void main(String[] args) {
        new KeyEventEx2();
    }
}
