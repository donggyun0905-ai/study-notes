package event;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Panel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class EventEx7 extends MFrame{

	Checkbox red, green, cyan;
	CheckboxGroup grp;
	Panel p;

	public EventEx7() {
		p = new Panel();
		grp = new CheckboxGroup();
		p.add(red = new Checkbox("Red", false, grp));
		p.add(green = new Checkbox("Green", false, grp));
		p.add(cyan = new Checkbox("Cyan", true, grp));

		setBackground(Color.CYAN);
		add(p, BorderLayout.SOUTH);

		red.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				setBackground(Color.RED);
				p.setBackground(Color.RED);
				//red.setBackground(Color.RED);//Checkbox 색상 변경
				validate();
			}
		});

		green.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				setBackground(Color.GREEN);
				p.setBackground(Color.GREEN);
				validate();
			}
		});

		cyan.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				setBackground(Color.CYAN);
				p.setBackground(Color.CYAN);
				validate();
			}
		});
	}

	public static void main(String[] args) {
		new EventEx7();
	}
}



