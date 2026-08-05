package swing;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;

public class ProgressBarEx {
    public static void main(String[] args) {
        JFrame frame = new JFrame("JProgressBar 색상 변경 예제");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setStringPainted(true); // 문자열 표시 여부
        progressBar.setForeground(Color.RED); // 색상 지정
        progressBar.setValue(50);

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(progressBar);

        Container contentPane = frame.getContentPane();
        contentPane.add(panel);

        frame.pack();
        frame.setVisible(true);
    }
}
