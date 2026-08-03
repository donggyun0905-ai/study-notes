package awt;

import java.awt.Button;
import java.awt.Color;

public class uttonEx1 extends MFrame{
	
	Button btn[] = new Button[4]; //컴포넌트는 필드 선언 <- 메소드 공유 목적
	String label[] = {"추가", "삭제", "전체삭제", "종료"};
	public uttonEx1() {
		super(250,250);
		setTitle("Button예제");
		for (int i = 0; i < btn.length; i++) {
			btn[i] = new Button(label[i]);
			Color c[] = MColor.rColor2();
			btn[i].setBackground(c[0]);
			btn[i].setBackground(c[1]);
			add(btn[i]);
			
		}
		validate();
		
	}
	
	
	public static void main(String[]args) {
		new uttonEx1();
	}
}
