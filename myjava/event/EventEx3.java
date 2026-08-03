package event;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventEx3 extends MFrame{

    Button btn;

    public EventEx3() {
        add(btn = new Button("버튼2"), BorderLayout.SOUTH);
        btn.addActionListener(new MyAction3());
    }

    class MyAction3 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            setBackground(MColor.rColor());
            btn.setForeground(MColor.rColor());
        }
    }

    //내부클래스로 인벤트 리스너 구현 : 외부클래스를 쉽게 접근 가능. 그러나 이클래스도 종속적이다.
    public static void main(String[] args) {
        new EventEx3();
    }
}
