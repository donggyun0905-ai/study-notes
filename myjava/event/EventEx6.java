package event;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class EventEx6 extends MFrame implements ItemListener{
	
	Checkbox red, green, cyan;
	CheckboxGroup grp;
	Panel p;

	public EventEx6() {
		p = new Panel();
		grp = new CheckboxGroup();
		p.add(red = new Checkbox("Red", false, grp));
		p.add(green = new Checkbox("Green", false, grp));
		p.add(cyan = new Checkbox("Cyan", true, grp));
		red.addItemListener(this);
		green.addItemListener(this);
		cyan.addItemListener(this);
		setBackground(Color.CYAN);
		add(p, BorderLayout.SOUTH);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		//1.Object 방식
		Object obj = e.getSource();
		if(obj == red){}

		//2. 이벤트 소스가 동일한 타입
		Checkbox cb = (Checkbox)e.getSource();
		//System.out.println(cb.getLabel());
		if(cb.getLabel().equalsIgnoreCase("red")){
			setBackground(Color.RED);
			p.setBackground(Color.RED);
			red.setBackground(Color.RED);//Checkbox 색상 변경
		}else if(cb.getLabel().equalsIgnoreCase("green")){
			setBackground(Color.GREEN);
			p.setBackground(Color.GREEN);
		}else if(cb.getLabel().equalsIgnoreCase("cyan")){
			setBackground(Color.CYAN);
			p.setBackground(Color.CYAN);
		}
		validate();
	}
	
	public static void main(String[] args) {
		new EventEx6();
	}
}












