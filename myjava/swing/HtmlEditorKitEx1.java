package swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

/**
 * JEditorPane, HTMLEditorKit, StyleSheet, JFrame을 사용하여 스타일이 있는 HTML 뷰어를 만드는 방법을 보여주는 완전한 Java 클래스.
 * 
 * 작성자: Alvin Alexander, devdaily.com.
 *
 */
public class HtmlEditorKitEx1 {
	public static void main(String[] args) {
		new HtmlEditorKitEx1();
	}

	public HtmlEditorKitEx1() {
				// JEditorPane 생성
				JEditorPane jEditorPane = new JEditorPane();

				// 읽기 전용으로 설정
				jEditorPane.setEditable(false);

				// JScrollPane 생성; 원하는 속성으로 수정 가능
				JScrollPane scrollPane = new JScrollPane(jEditorPane);

				// HTML 편집기 키트 추가
				HTMLEditorKit kit = new HTMLEditorKit();
				jEditorPane.setEditorKit(kit);

				// HTML에 스타일 추가
				StyleSheet styleSheet = kit.getStyleSheet();
				styleSheet.addRule("body {color:#000; font-family:times; margin: 4px; }"); // 본문 스타일
				styleSheet.addRule("h1 {color: blue;}"); // H1 스타일
				styleSheet.addRule("h2 {color: #ff0000;}"); // H2 스타일
				styleSheet.addRule("pre {font : 10px monaco; color : black; background-color : #fafafa; }"); // pre 태그 스타일

				// 간단한 HTML 문자열 생성
				String htmlString = "<html>\n" + 
					"<body>\n" + 
					"<h1>Welcome!</h1>\n" + 
					"<h2>This is an H2 header</h2>\n"
					+ "<p>This is some sample text</p>\n"
					+ "<p><a href=\"http://devdaily.com/blog/\">devdaily blog</a></p>\n" 
					+ "</body>\n";

				// 문서 생성 후 JEditorPane에 설정하고 HTML 추가
				Document doc = kit.createDefaultDocument();
				jEditorPane.setDocument(doc);
				jEditorPane.setText(htmlString);

				// 모든 것을 프레임에 추가
				JFrame j = new JFrame("HtmlEditorKit Test");
				j.getContentPane().add(scrollPane, BorderLayout.CENTER);

				// 애플리케이션을 쉽게 닫을 수 있도록 설정
				j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				// 프레임을 표시
				j.setSize(new Dimension(300, 200));

				// 필요시 프레임의 크기를 내용물에 맞게 조정
				// j.pack();

				// JFrame을 화면 중앙에 위치시키고 표시
				j.setLocationRelativeTo(null);
				j.setVisible(true);
	}
}
