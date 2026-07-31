package awt;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogEx1 extends MFrame implements ActionListener{

    Button btn;
    String title = "메세지 대화상자";

    public DialogEx1() {
        super(300,300);
        setTitle("Dialog 예제");
        add(btn = new Button("보이기"),BorderLayout.SOUTH);
        btn.addActionListener(this);
        validate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MyDialog md = new MyDialog(this,title,true);
        md.setSize(150,100);
        //Frame에 중앙에 위치
        md.setLocationRelativeTo(this);
        //System.out.println(getWidth() + " : " + getHeight());
        //System.out.println(getX() + " : " + getY());

        md.setVisible(true);
    }

    class MyDialog extends Dialog{

        Button b;

        public MyDialog(Frame owner, String title,boolean modal){
            super(owner,title,modal);
            setLayout(new FlowLayout());
            b = new Button("확인");
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();//화면에서 사라짐
                }
            });
            add(b);
        }



    }

    public static void main(String[] args) {
        new DialogEx1();
    }
}
