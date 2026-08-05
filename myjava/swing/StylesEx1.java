package swing;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class StylesEx1 {
  public static void main(String[] args) {
    try {
        // UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    } catch (Exception evt) {
      // LookAndFeel 설정이 실패할 경우 예외를 처리합니다.
    }

    JFrame f = new JFrame("Styles Example 6");
    
    // StyleContext, Document, JTextPane을 생성합니다.
    StyleContext sc = new StyleContext();
    final DefaultStyledDocument doc = new DefaultStyledDocument(sc);
    final JTextPane pane = new JTextPane(doc);
    
    // 메인 문서 스타일을 생성하고 추가합니다.
    Style defaultStyle = sc.getStyle(StyleContext.DEFAULT_STYLE);
    final Style mainStyle = sc.addStyle("MainStyle", defaultStyle);
    StyleConstants.setLeftIndent(mainStyle, 16);  // 왼쪽 들여쓰기 설정
    StyleConstants.setRightIndent(mainStyle, 16); // 오른쪽 들여쓰기 설정
    StyleConstants.setFirstLineIndent(mainStyle, 16); // 첫 번째 줄 들여쓰기 설정
    StyleConstants.setFontFamily(mainStyle, "serif"); // 글꼴 패밀리 설정
    StyleConstants.setFontSize(mainStyle, 12); // 글꼴 크기 설정

    // 고정 폭 스타일을 생성하고 추가합니다.
    final Style cwStyle = sc.addStyle("ConstantWidth", null);
    StyleConstants.setFontFamily(cwStyle, "monospaced"); // 고정 폭 글꼴 설정
    StyleConstants.setForeground(cwStyle, Color.green);  // 글꼴 색상 설정

    // 헤딩 스타일을 생성하고 추가합니다.
    final Style heading2Style = sc.addStyle("Heading2", null);
    StyleConstants.setForeground(heading2Style, Color.red); // 글꼴 색상 설정
    StyleConstants.setFontSize(heading2Style, 16); // 글꼴 크기 설정
    StyleConstants.setFontFamily(heading2Style, "serif"); // 글꼴 패밀리 설정
    StyleConstants.setBold(heading2Style, true); // 글꼴을 굵게 설정
    StyleConstants.setLeftIndent(heading2Style, 8); // 왼쪽 들여쓰기 설정
    StyleConstants.setFirstLineIndent(heading2Style, 0); // 첫 번째 줄 들여쓰기 설정

    try {
      SwingUtilities.invokeAndWait(new Runnable() {
        public void run() {
          try {
            // 논리적 스타일을 설정합니다.
            doc.setLogicalStyle(0, mainStyle);

            // 문서에 텍스트를 추가합니다.
            doc.insertString(0, text, null);

            // 특정 문자 속성을 적용합니다.
            doc.setCharacterAttributes(49, 13, cwStyle, false);
            doc.setCharacterAttributes(223, 14, cwStyle, false);
            doc.setCharacterAttributes(249, 14, cwStyle, false);
            doc.setCharacterAttributes(286, 8, cwStyle, false);
            doc.setCharacterAttributes(475, 14, cwStyle, false);
            doc.setCharacterAttributes(497, 21, cwStyle, false);
            doc.setCharacterAttributes(557, 9, cwStyle, false);
            doc.setCharacterAttributes(639, 12, cwStyle, false);
            doc.setCharacterAttributes(733, 21, cwStyle, false);
            doc.setCharacterAttributes(759, 9, cwStyle, false);

            // 마지막으로 헤딩 스타일을 적용합니다.
            doc.setParagraphAttributes(0, 1, heading2Style, false);

            // 전경색을 설정하고 글꼴을 변경합니다.
            pane.setForeground(Color.pink);
            pane.setFont(new Font("Monospaced", Font.ITALIC, 24));
          } catch (BadLocationException e) {
            // 잘못된 위치 예외를 처리합니다.
          }
        }
      });
    } catch (Exception e) {
      // 문서를 구성할 때 발생한 예외를 처리하고 프로그램을 종료합니다.
      System.out.println("Exception when constructing document: " + e);
      System.exit(1);
    }

    f.getContentPane().add(new JScrollPane(pane));
    f.setSize(400, 300);
    f.setVisible(true);
  }

  // 문서에 삽입할 텍스트
  public static final String text = 
          "Attributes, Styles and Style Contexts\n" +
          "The simple PlainDocument class that you saw in the previous " + 
          "chapter is only capable of holding text. The more complex text " +
          "components use a more sophisticated model that implements the " +
          "StyledDocument interface. StyledDocument is a sub-interface of " +
          "Document that contains methods for manipulating attributes that " +
          "control the way in which the text in the document is displayed. " +
          "The Swing text package contains a concrete implementation of " +
          "StyledDocument called DefaultStyledDocument that is used as the " +
          "default model for JTextPane and is also the base class from which " +
          "more specific models, such as the HTMLDocument class that handles " +
          "input in HTML format, can be created. In order to make use of " +
          "DefaultStyledDocument and JTextPane, you need to understand how " +
          "Swing represents and uses attributes.\n";

}
