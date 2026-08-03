package event;

import jdk.jfr.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MyAction implements ActionListener{
    EventEx2 f;

    public MyAction(EventEx2 f){
        this.f = f;
    }

    //기능 : 버튼을 클릭하면 Frame의 배경색과 Button 배경색이 랜덤하게 세팅
    @Override
    public void actionPerformed(ActionEvent e) {
        f.setBackground(MColor.rColor());
        f.btn.setBackground(MColor.rColor());
    }
}

public class EventEx2 extends MFrame{
    Button btn;

    public EventEx2() {
        add(btn = new Button("버튼2"), BorderLayout.SOUTH);
        MyAction ma = new MyAction(this);
        btn.addActionListener(ma);
    }

    public static void main(String[] args) {
        new EventEx2();
    }
}
