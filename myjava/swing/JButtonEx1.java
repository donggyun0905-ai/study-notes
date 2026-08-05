package swing;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class JButtonEx1 extends JFrame{
	
	public JButtonEx1() {
		setSize(300, 300);
		setLayout(new FlowLayout());
		JButton btn = new JButton("버튼");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btn.setBackground(Color.ORANGE);
			}
		});
		btn.setForeground(Color.RED);
		JLabel label = new JLabel("라벨");
		label.setForeground(Color.GREEN);
		add(btn);
		add(label);
	}
	
	public static void main(String[] args) {
		new JButtonEx1().setVisible(true);
	}
}




