package event;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionEventEx2 extends MFrame
implements ActionListener{

	List list;
	Button b[] = new Button[4];
	String lab[] = {"추가", "지우기", "전체지우기", "종료"};
	TextField tf;
	
	public ActionEventEx2() {
		super(300, 200);
		//////////////////////////////////////////////
		Panel p = new Panel();
		p.setLayout(new GridLayout(4,1));
		for (int i = 0; i < b.length; i++) {
			p.add(b[i]=new Button(lab[i]));
			b[i].addActionListener(this);
		}
		//////////////////////////////////////////////
		add(list=new  List());
		list.add("짜장면");
		list.add("짬뽕");
		add(p, BorderLayout.EAST);
		add(tf=new TextField(), BorderLayout.SOUTH);
		tf.addActionListener(this);
		validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		//System.out.println(cmd);
		if(cmd.equals(lab[0])||e.getSource()==tf){
			String str= tf.getText().trim();
			if(str.length()==0){
				new DialogBox(this, "문자열을 입력하세요","경고");
				return;
			}else{
				list.add(str);
			}
			tf.setText("");
			tf.requestFocus();
		}else if(cmd.equals(lab[1])){
			int idx = list.getSelectedIndex();
			if(idx == -1){
				new DialogBox(this, "아이템을 선택하세요","경고");
				return;
			}
			list.remove(idx);//선택된 아이템 삭제
		}else if(cmd.equals(lab[2])){
			list.removeAll();
		}else if(cmd.equals(lab[3])){
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		new ActionEventEx2();
	}
}







