package swing;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JButtonEx2 extends JFrame {

	JButtonEx2() {
		setTitle("My Frame");
		setSize(500, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 10));
		JButton[] buttons = new JButton[10];
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton();
			buttons[i].setText("" + i);
			panel.add(buttons[i]);
		}
		this.add(panel);
		setVisible(true);
	}

	public static void main(String[] args) {
		JButtonEx2 m = new JButtonEx2();
	}

}
