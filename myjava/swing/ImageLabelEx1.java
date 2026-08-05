package swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImageLabelEx1 extends JFrame 
implements ActionListener{
	
	JPanel p;
	JLabel label;
	JButton btn;
	
	public ImageLabelEx1() {
		setTitle("이미지 레이블");
		setSize(300, 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		p = new JPanel();
		label = new JLabel("이미지를 보려면 아래 버튼을 누르세요.");
		btn = new JButton("이미지 레이블");
		ImageIcon icon = new ImageIcon("swing/icon.gif");
		btn.setIcon(icon);
		btn.addActionListener(this);
		p.add(label);
		p.add(btn);
		add(p);
		setVisible(true);
		}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ImageIcon dog = new ImageIcon("swing/dog.gif");
		label.setIcon(dog);
		label.setText(null);
	}

	public static void main(String[] args) {
		new ImageLabelEx1();
	}
}
