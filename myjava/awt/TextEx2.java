package awt;

import org.w3c.dom.Text;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextEx2 extends MFrame{

    TextArea ta;
    TextField tf;
    Button btn;

    public TextEx2() {
        super(260,300);
        setTitle("MyChat v1.0");
        setLayout(new BorderLayout());
        add(ta = new TextArea());
        ta.setEditable(false);
        Color c[] = MColor.rColor2();
        ta.setBackground(c[0]);
        ta.setForeground(c[1]);
        //Panel: middle 컨테이너
        Panel p = new Panel();
        p.add(tf = new TextField(22));
        p.add(btn = new Button("SEND"));
        add(p,BorderLayout.SOUTH);
        tf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = tf.getText().trim();
                if(str.length() == 0){
                    str = "입력을 하세요";
                }
                ta.append(str + "\n");
                tf.setText("");
                tf.requestFocus();
            }
        });

        tf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = tf.getText().trim();
                if(str.length() == 0){
                    str = "입력을 하세요";
                }
                ta.append(str + "\n");
                tf.setText("");
                tf.requestFocus();
            }
        });
        validate();
    }

    public static void main(String[] args) {
        new TextEx2();
    }
}
