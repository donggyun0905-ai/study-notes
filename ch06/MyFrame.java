package ch06;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends MFrame{
    Button btn;

    MyFrame(){
        btn = new Button("버튼");
        add(btn,"South");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBackground(MColor.rColor());
            }
        });
    }

    public static void main(String[] args) {
        new MyFrame();
    }
}
