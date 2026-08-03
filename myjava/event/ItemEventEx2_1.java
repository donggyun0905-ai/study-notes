package event;

import java.awt.Checkbox;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ItemEventEx2_1 extends MFrame implements ItemListener {

    Checkbox cbs[] = new Checkbox[3];
    String label[] = {"음악","영화","스포츠"};
    TextField tf;

    public ItemEventEx2_1() {
        super(400, 150);
        setLayout(new FlowLayout());
        add(new Label("취미를 선택하세요: "));
        for (int i = 0; i < cbs.length; i++) {
            cbs[i] = new Checkbox(label[i]);
            cbs[i].addItemListener(this);
            add(cbs[i]);
        }
        tf = new TextField(30);
        add(tf);
        tf.setEditable(false);
        validate();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        StringBuffer sb = new StringBuffer();
        for (Checkbox cb:cbs) {
            if(cb.getState()){
                sb.append(cb.getLabel()).append(" ");
            }
        }
        tf.setText("선택된 취미: " + sb.toString().trim());
    }

    public static void main(String[] args) {
        new ItemEventEx2_1();
    }
}





