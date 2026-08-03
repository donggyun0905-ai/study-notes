package event;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventEx5 extends MFrame implements ActionListener{

	Button btn[] = new Button[3];
	String label[] = {"버튼1", "버튼2", "버튼3"};
	
	public EventEx5() {
		Panel p = new Panel();
		for (int i = 0; i < btn.length; i++) {
			btn[i] = new Button(label[i]);
			p.add(btn[i]);
			btn[i].addActionListener(this);
		}
		add(p, BorderLayout.SOUTH);

	}
	
	@Override 
	public void actionPerformed(ActionEvent e) {
		//ActionEvent 객체를 생성시킨 source 리턴
		Object obj = e.getSource();
		if(obj == btn[0]){
			setBackground(MColor.rColor());
		}else if(obj==btn[1]){
			for (int i = 0; i < btn.length; i++) {
				btn[i].setBackground(MColor.rColor());
			}
		}else if(obj == btn[2]){
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		new EventEx5();
	}
}





