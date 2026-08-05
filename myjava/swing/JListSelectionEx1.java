package swing;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.UIManager;

public class JListSelectionEx1 extends JFrame {

    private String[] exampleText = {
            "Some example text without any color changes",
            "Some more example text without color changes",
            "Even more plain text!",
            "<html>Uncolored Text! <font color=orange>Now some example Text with color!</font> more Uncolored Text!</html>",
            "<html>Uncolored Text! <font color=green>And some more example text with color! Text, Text, Text!</font> more Uncolored Text!</html>",
            "<html>Uncolored Text! <font color=red>A string with red color, Text Text Text!</font> more Uncolored Text!</html>",
            "<html>Uncolored Text! <font color=blue>And finally a string with blue color, Text Text Text!</font> more Uncolored Text!</html>",
            "<html>Uncolored Text! <font color=purple><span style='background-color: white;'>Testing if some html can turn the selection color white!</span></font> more Uncolored Text!</html>"
    };

    public JListSelectionEx1() {
        super("JList Selection Example");

        // LookAndFeel 설정은 컴포넌트 생성 전에 해야 합니다.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // JList 생성
        JList<String> list = new JList<String>(exampleText);

        // 선택된 항목의 텍스트 색상 설정
        list.setSelectionForeground(Color.WHITE);

        // 배경색을 테마에 맞게 설정
        list.setSelectionBackground(Color.DARK_GRAY); // 선택 시 배경색 설정

        add(list);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JListSelectionEx1();
            }
        });
    }
}
