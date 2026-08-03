package event;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventEx4 extends MFrame {
	
	Button btn1, btn2; 
	
	public EventEx4() {
		Panel p = new Panel();
		p.add(btn1 = new Button("버튼1"));
		p.add(btn2 = new Button("버튼2"));
		add(p, BorderLayout.SOUTH);
		//익명클래스는 이벤트 리스너를 선언과 동시에 객체를 생성하여 이벤트 소스랑 연결
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setBackground(MColor.rColor());
			}
		});
		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color c[] = MColor.rColor2();
				btn1.setBackground(c[0]);
				btn2.setBackground(c[1]);
			}
		});
	}

	public static void main(String[] args) {
		new EventEx4();
	}
}







