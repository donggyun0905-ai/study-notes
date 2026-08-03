package event;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*2. 버튼을 클릭하면 ActionEvent 객체가 내부적을 생성
* 이벤트 소프랑 ActionEvent 객체를 연결하기 위한 리스너를 구현.
* XxxListener. Listener : 이벤트를 듣고(Listen) 이벤트가 발생하면 반응하는 역할
* */

public class EventEx1 extends MFrame implements ActionListener {

    Button btn;

    public EventEx1() {
        //1. 이벤트 생성
        add(btn = new Button("버튼"),BorderLayout.SOUTH);
        //4. 이벤트 소스랑 이벤트 핸들러 연결(addXxxLinstener)
        btn.addActionListener(this);
    }

    //3. 이벤트 리서너의 메소드(이벤트 핸들러) 를 구현
    @Override
    public void actionPerformed(ActionEvent e) {
        setBackground(MColor.rColor());
    }

    public static void main(String[] args) {
        new EventEx1();
    }
}
