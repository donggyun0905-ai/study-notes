package ch08;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends MFrame implements ActionListener {

    Button btn;

    public MyFrame() {
        btn = new Button("버튼");
        add(btn,BorderLayout.SOUTH);
        //addActionListener() 메소드에 매개변수 타입은 ActionListener
        //그래서 현재 객체의 타입이 Frame or ActionListener 타입
        //btn이랑 클릭하면 actionPerformed() 호출
        //Frame과 ActionEvent 객체의 브릿지 역할을 하는 인터페이스가 ActionListener
        btn.addActionListener(this);
    }
    /*Button : 집에 초인종
    * 사용자 : button 클릭
    * ActionListener : 초인종 소리를 기다리고 있는 사람
    * actionPerformed() 메소드 : 초인종이 울렸을 때 실행할 행동 ex) 누구세요?
    * */

    @Override
    public void actionPerformed(ActionEvent e) {
        setBackground(MColor.rColor());
    }

    public static void main(String[] args) {
        new MyFrame();
    }
}
