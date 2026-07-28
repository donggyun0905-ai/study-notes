package ch09;

import ch08.MColor;
import com.mysql.cj.jdbc.exceptions.PacketTooBigException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

public class MyFrame2 extends MFrame implements ActionListener{

    Button btn1,btn2;

    public MyFrame2() {
        setTitle("제목2");
        Panel p = new Panel();
        p.add(btn1 = new Button("Btn1"));
        p.add(btn2 = new Button("Btn2"));
        btn1.addActionListener(this);
        btn2.addActionListener(this);
        System.out.println("btn1: " + btn1.hashCode());
        System.out.println("btn2: " + btn2.hashCode());
        add(p,BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        //System.out.println(e.getSource().hashCode());//객체 주소값 리턴
        if(e.getSource()==btn1){
            setBackground(MColor.rColor());
        }else if(e.getSource()==btn2){
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new MyFrame2();

    }
}
