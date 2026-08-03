package graphics;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ImageViewEx2 extends MFrame 
implements ActionListener{
	
	Image img[] = new Image[3];
	Button btn[] = new Button[4];
	String label[] = {"첫번째", "두번째", "세번째", "랜덤"};
	Random r = new Random();
	int idx = 0;
	
	public ImageViewEx2() {
		super(550, 300);
		Panel p = new Panel();
		p.setLayout(new GridLayout(4, 1));
		for (int i = 0; i < btn.length; i++) {
			btn[i] = new Button(label[i]);
			btn[i].addActionListener(this);
			p.add(btn[i]);
		}
		//이미지를 메모리 로딩
		for (int i = 0; i < img.length; i++) {
			img[i] = Toolkit.getDefaultToolkit().
					getImage("graphics/aaa"+i+".png");
		}
		add(p, BorderLayout.EAST);
		validate();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object cmd = e.getSource();

		for (int i = 0; i < btn.length; i++) {
			if(cmd == btn[i]){
				idx = (i == btn.length-1)? r.nextInt(btn.length-1):i;
				break;
			}
		}
		repaint();
	}
	
	//직접적인 paint 호출는 안되고 repaint() 호출됨
	@Override
	public void paint(Graphics g) {
		g.drawImage(img[idx],0,0,this);
	}
	
	public static void main(String[] args) {
		new ImageViewEx2();
	}
}
