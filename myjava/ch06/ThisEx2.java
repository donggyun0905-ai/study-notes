package ch06;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThisEx2 extends MFrame implements ActionListener {

    Button btn;

    public ThisEx2() {
        btn = new Button("버튼");
        add(btn,"South");
        //현재 객체가 들어가야함
        btn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        btn.setBackground(MColor.rColor());
        setBackground(MColor.rColor());
    }

    public static void main(String[] args) {
        //레퍼런스 변수가 필요없는 경우
        new ThisEx2();
    }
}
