package swing;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class HtmlAWTEx1 extends JFrame implements ActionListener {

	JTextField jt;  // 텍스트 입력 필드
	JButton btn;    // 전송 버튼
	Choice ch;      // 색상 선택 메뉴
	JTextPane editorPane;  // HTML을 표시할 JTextPane

	public HtmlAWTEx1() {
		setTitle("HTML Example"); // 창 제목 설정
		setSize(550, 200); // 창 크기 설정

		editorPane = new JTextPane();
		editorPane.setEditable(false); // 읽기 전용으로 설정
		editorPane.setContentType("text/html"); // HTML 콘텐츠 타입 설정

		JScrollPane scrollPane = new JScrollPane(editorPane);
		getContentPane().add(scrollPane); // 스크롤 가능한 패널에 추가
		JPanel p = new JPanel();
		p.add(jt = new JTextField(30)); // 30 열의 JTextField 추가
		p.add(btn = new JButton("SEND")); // 전송 버튼 추가
		p.add(ch = new Choice()); // AWT Choice 추가 (드롭다운 메뉴)
		ch.add("RED");  // 색상 선택 항목 추가
		ch.add("GREEN");
		ch.add("BLUE");
		btn.addActionListener(this); // 버튼에 액션 리스너 추가
		jt.addActionListener(this);  // 텍스트 필드에 액션 리스너 추가 (Enter 키 반응)
		jt.requestFocus(); // 텍스트 필드에 포커스 설정
		add(p, BorderLayout.SOUTH); // 패널을 창의 남쪽에 추가
		setVisible(true); // 창을 보이도록 설정
		validate(); // 컴포넌트가 올바르게 배치되었는지 확인
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String color = ch.getSelectedItem(); // 선택한 색상 가져오기
		String str = "<font color='" + color + "'>" + jt.getText() + "</font><br>"; // 입력된 텍스트에 HTML 색상 태그 추가
		editorPane.setText(editStr(editorPane.getText() + str)); // 기존 텍스트에 새로운 텍스트 추가
		jt.setText(""); // 텍스트 필드 비우기
		jt.requestFocus(); // 텍스트 필드에 포커스 설정
	}
	
	public String editStr(String str) {
		// HTML 태그를 제거하여 텍스트 내용만 남기기
		str =  str.replace("<html>", "");
		str =  str.replace("<head>", "");
		str =  str.replace("</head>", "");
		str =  str.replace("<body>", "");
		str =  str.replace("</body>", "");
		str =  str.replace("</html>", "");
		return str;
	}

	public static void main(String[] args) {
		HtmlAWTEx1 htmlExample = new HtmlAWTEx1();
		htmlExample.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창을 닫으면 애플리케이션 종료
	}
}
