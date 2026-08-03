package event;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseEventEx2 extends MFrame {
    Button btn;

    public MouseEventEx2() {
        super(400,500,new Color(100,200,100));
        setLayout(null);
        btn = new Button("난 버튼");
        btn.setBackground(MColor.rColor());
        btn.setBounds(200,250,100,50);
        //익명클래스로 MouseAdapter 이용
        //버튼에 마우스가 들어오고 나가고 했을때 Frame 랜덤 배경색 변경
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(MColor.rColor());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(MColor.rColor());
            }
        });
        add(btn);
    }



    public static void main(String[] args) {
        new MouseEventEx2();
    }
}
