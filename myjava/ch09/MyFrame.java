package ch09;

import ch08.MColor;
import com.mysql.cj.jdbc.exceptions.PacketTooBigException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends MFrame{

    Button btn1,btn2;

    public MyFrame() {
        Panel p = new Panel();
        p.add(btn1 = new Button("Btn1"));
        p.add(btn2 = new Button("Btn2"));

        //btn1은 배경색 변경, btn2는 종료의 기능을 익명클래스로 구현
        //ActionListener() 메소드 매개변수로 actionListener 타입의 객체가 들어감
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBackground(MColor.rColor());
            }
        });

        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //프로그램 종료
                System.exit(0);//0은 정상적인 종료
            }
        });
        validate();

        add(p,BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        new MyFrame();

    }
}
