package event;

import java.awt.Checkbox;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ItemEventEx2 extends MFrame implements ItemListener {

    Checkbox cb1, cb2, cb3;
    TextField tf;

    public ItemEventEx2() {
        super(400, 150);
        setLayout(new FlowLayout());
        add(new Label("취미를 선택하세요: "));
        cb1 = new Checkbox("Music");
        cb2 = new Checkbox("Movie");
        cb3 = new Checkbox("Sports");
        add(cb1); add(cb2); add(cb3);
        tf = new TextField(30);
        add(tf);
        // 각각의 체크박스에 리스너 등록
        cb1.addItemListener(this);
        cb2.addItemListener(this);
        cb3.addItemListener(this);
        tf.setEditable(false);
        validate();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        String str = "선택한 취미 : ";

        if(cb1.getState()){str += cb1.getLabel() + " ";}
        if(cb2.getState()){str += cb2.getLabel() + " ";}
        if(cb3.getState()){str += cb3.getLabel() + " ";}

        tf.setText(str);
    }

    public static void main(String[] args) {
        new ItemEventEx2();
    }
}





