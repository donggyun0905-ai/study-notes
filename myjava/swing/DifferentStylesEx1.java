package swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class DifferentStylesEx1 {
    
    Random random = new Random(); // 랜덤 색상 생성을 위한 Random 객체
    JFrame frame; // 애플리케이션 창
    JTextPane textPane; // 텍스트 표시용 JTextPane
    JTextField jt; // 입력 텍스트용 JTextField
    JButton btn; // 전송 버튼

    private void initUI() {
        frame = new JFrame(DifferentStylesEx1.class.getSimpleName()); // 창 제목 설정
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 닫기 버튼 누르면 애플리케이션 종료

        // 스타일이 있는 문서를 생성할 수 있는 JTextPane
        textPane = new JTextPane();
        textPane.setContentType("text/html"); // JTextPane을 HTML 콘텐츠로 설정
        JPanel p = new JPanel(); // JTextField와 JButton을 포함하는 패널 생성
        p.add(jt = new JTextField(20)); // 20열 크기의 JTextField 추가
        p.add(btn = new JButton("SEND")); // "SEND" 라벨의 JButton 추가

        // JTextField의 ActionListener 설정
        jt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = jt.getText(); // 입력된 텍스트 가져오기
                str = textPane.getText() + "<br>" + jt.getText(); // 기존 텍스트에 새 텍스트 추가 (HTML <br> 태그 사용)
                // SimpleAttributeSet set = new SimpleAttributeSet();
                // StyleConstants.setForeground(set, setRandom()); // 랜덤 색상 설정
                // doc.setCharacterAttributes(0, len, set, true);

                jt.setText(""); // JTextField를 비웁니다.
                textPane.setText(str); // 새 텍스트로 JTextPane 업데이트
            }
        });

        // 패널과 스크롤 가능한 JTextPane을 프레임에 추가
        frame.add(p, BorderLayout.SOUTH); // 패널을 프레임의 남쪽에 추가
        frame.add(new JScrollPane(textPane)); // 스크롤 가능한 JTextPane 추가
        frame.setSize(500, 400); // 프레임 크기 설정
        frame.setVisible(true); // 프레임을 보이도록 설정
    }

    // 랜덤 색상을 생성하는 메서드
    public Color setRandom() {
        return new Color(random.nextInt(256), 
                random.nextInt(256), 
                random.nextInt(256));
    }
    
    public static void main(String[] args) {
           new DifferentStylesEx1().initUI(); // UI 초기화 및 실행
    }
}
