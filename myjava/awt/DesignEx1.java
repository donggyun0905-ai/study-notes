package awt;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class DesignEx1 extends MFrame{
	
	Label label;
	Checkbox cb1, cb2, cb3;
	CheckboxGroup cbg;
	Button btn1, btn2;
	
	public DesignEx1() {
		super(250, 150);
		setLayout(new BorderLayout());
		setTitle("디자인 예제1");

		Panel top,middle,bottom;
		top = new Panel();
		top.setBackground(Color.GREEN);
		top.add(label = new Label("과일중에 선택",Label.CENTER));

		cbg = new CheckboxGroup();
		cb1 = new Checkbox("사과",cbg,false);
		cb2 = new Checkbox("딸기",cbg,true);
		cb3 = new Checkbox("앵두",cbg,false);

		middle = new Panel();
		middle.add(cb1);
		middle.add(cb2);
		middle.add(cb3);

		btn1 = new Button("Start");
		btn2 = new Button("End");

		bottom = new Panel();
		bottom.add(btn1);
		bottom.add(btn2);


		add(top,BorderLayout.NORTH);
		add(middle,BorderLayout.CENTER);
		add(bottom,BorderLayout.SOUTH);
		validate();
	}

	public static void main(String[] args) {
		new DesignEx1();
	}
}




