package graphics;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MFrame extends Frame{
	
	MFrame(){
		this(300,300,new Color(220,220,220));
	}
	MFrame(int w , int h){
		this(w,h,new Color(220,220,220));
	}
	MFrame(Color c){
		this(300, 300 ,c);
	}
	
	
	
	
	MFrame(int w, int h, Color c) {
		super();
		//Frame의 기본 레이아웃 : border
		//setLayout(new FlowLayout());
		setTitle("제목");
		setSize(w, h);
		setBackground(c);
		setResizable(false);
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		validate();
	}
	public static void main(String[] args) {
		MFrame mf = new MFrame(500,200,Color.orange);
	}
}
