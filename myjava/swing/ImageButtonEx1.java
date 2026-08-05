package swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class ImageButtonEx1 extends JFrame{
	
	JPanel p;
	JLabel label;
	JButton btn;
	ImageIcon icon, dog;
	
	public ImageButtonEx1() {
		setSize(300, 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		p = new JPanel();
		label = new JLabel("이미지를 보려면 아래 버튼을 누르세요.");
		btn = new JButton();
		icon = new ImageIcon("swing/icon.gif");
		btn.setIcon(icon);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dog = new ImageIcon("swing/dog.gif");
				label.setIcon(dog);
				label.setText(null);
			}
		});
		p.add(label);
		p.add(btn);
		add(p);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new ImageButtonEx1();
	}

}
