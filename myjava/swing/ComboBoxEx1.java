package swing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ComboBoxEx1 extends JFrame 
implements ActionListener {
	
	String ani[] = {"dog","tiger","lion","aaa"};
	JLabel label;
	JComboBox com;

	public ComboBoxEx1() {
		setTitle("ComboBoxTest");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		///////////////////////////////////////////
		com = new JComboBox(ani);
		label = new JLabel();
		label.setHorizontalAlignment(JLabel.CENTER);
		com.setSelectedItem(0);
		changePicture(ani[com.getSelectedIndex()]);
		com.addActionListener(this);
		///////////////////////////////////////////
		add(com,BorderLayout.PAGE_START);
		add(label,BorderLayout.PAGE_END);
		setVisible(true);
	}
	
	public void changePicture(String name){
		ImageIcon icon = new ImageIcon("ch17/"+name+".gif");
		label.setIcon(icon);
		int loadStatus = icon.getImageLoadStatus();
		if(loadStatus==8){
			label.setText(null);
		}else if(loadStatus==4){
			label.setText("이미지가 없습니다.");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String name = (String)com.getSelectedItem();
		changePicture(name);
	}

	public static void main(String[] args) {
		new ComboBoxEx1();
	}

}
