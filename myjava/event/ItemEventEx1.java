package event;

import java.awt.Choice;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ItemEventEx1 extends MFrame implements ItemListener {

    Choice ch;
    String[] colors = {"White", "Red", "Green", "Blue", "Yellow"};

    public ItemEventEx1() {
        super(300, 200);
        setLayout(new FlowLayout());
        ch = new Choice();
        for (String c : colors) {
            ch.add(c);
        }
        add(ch);
        ch.addItemListener(this);
        validate();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        String item = (String)e.getItem();
        //System.out.println(item);
        switch(item){
            case"Red":setBackground(Color.RED);break;
            case"Green":setBackground(Color.GREEN);break;
            case"Blue":setBackground(Color.BLUE);break;
            case"Yellow":setBackground(Color.YELLOW);break;
            default:setBackground(Color.WHITE);
        }
        setTitle("Selected: " + item);
    }

    public static void main(String[] args) {
        new ItemEventEx1();
    }
}






