package swing;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class JTextAreaColorEx1 {
   public static void main(String[] args) {
      JFrame frame = new JFrame("JTextArea Color Example");

      JTextArea textArea = new JTextArea();
      textArea.setEditable(false);

      // 색상 변경할 문자열 입력
      String text = "Java Swing JTextArea Color Example";

      // 색상 변경할 문자열 길이
      int length = text.length();

      // StyleContext와 AttributeSet 객체 생성
      StyleContext sc = StyleContext.getDefaultStyleContext();
      AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.RED);
      
      Style cwStyle = sc.addStyle("ConstantWidth", null);
      StyleConstants.setFontFamily(cwStyle, "monospaced");
      StyleConstants.setForeground(cwStyle, Color.green);
      
      DefaultStyledDocument doc = new DefaultStyledDocument(sc);
      JTextPane pane = new JTextPane(doc);
      
      // 문자열의 각 문자마다 색상 변경
      for (int i = 0; i < length; i++) {
    	 doc.setCharacterAttributes(0, length, cwStyle, true);
         textArea.replaceSelection(String.valueOf(text.charAt(i)));
      }

      textArea.add(pane);
      frame.add(textArea);
      frame.setSize(400, 300);
      frame.setVisible(true);
   }
}
