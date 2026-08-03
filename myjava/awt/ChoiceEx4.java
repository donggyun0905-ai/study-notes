package awt;

import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;


import java.awt.Choice;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ChoiceEx4 extends MFrame implements ItemListener {
	
	String mf[] = {"남자연예인", "여자연예인"};
	String ms[] = {"현 빈", "원 빈", "이민호", "김수현", "김우빈", "이종석"};
	String fs[] = {"고아라", "이연희", "이하늬", "문채원", "수 지", "김연아"};
	Choice name, star;
	String str;

	public ChoiceEx4() {
		super(300, 300, new Color(100, 200, 100));
		setTitle("Choice 예제3");
		
		star = new Choice();
		for (int i = 0; i < mf.length; i++) {
			star.add(mf[i]);
		}
		
		name = new Choice();
		for (int i = 0; i < ms.length; i++) {
			name.add(ms[i]);
		}
		
		// star와 name 모두 아이템 선택 이벤트 등록
		star.addItemListener(this);
		name.addItemListener(this); // name을 바꿨을 때도 paint 화면이 갱신되도록 추가
		
		add(star);
		add(name);
	} // <- 생성자 닫는 중괄호 추가
	
	/*public void inputItem(Choice c, String items[]) {
		c.removeAll();
		for (int i = 0; i < items.length; i++) {
			c.add(items[i]);
		}
	}*/
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLUE);
		String str = "남/여 연예인 선택: " + star.getSelectedItem();
		g.drawString(str, 30, 120);
		
		g.setColor(Color.RED);
		str = "이름 선택: " + name.getSelectedItem();
		g.drawString(str, 30, 150);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// 이벤트가 star(성별 선택)에서 발생했을 때 목록 업데이트
		if (e.getSource() == star) {
			
			name.removeAll();
			// 선택된 항목의 글자를 가져옴 ("남자연예인" 또는 "여자연예인")
	        String selectedGender = star.getSelectedItem(); 
	        
	        if (selectedGender.equals("남자연예인")) {
	            for (int i = 0; i < ms.length; i++) {
	                name.add(ms[i]);
	            }
	        } else if (selectedGender.equals("여자연예인")) {
	            for (int i = 0; i < fs.length; i++) {
	                name.add(fs[i]);
				}
			}
		}
		
		
		repaint();
	}
	
	public static void main(String[] args) {
		new ChoiceEx4();
	}
}