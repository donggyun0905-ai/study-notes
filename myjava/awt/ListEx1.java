package awt;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ListEx1 extends MFrame{

    List list1, list2;
    String team1[] ={"롯데","삼성","한화","KIA","NC"};
    String team2[] ={"LG","두산","넥센","SK","KT"};
    Label label1, label2;

    public ListEx1() {
        list1 = new List(3,false);//단일 선택
        list2 = new List(4,true);//복수 선택
        for (int i = 0; i < team1.length; i++) {
            list1.add(team1[i]);
            list2.add(team2[i]);
        }
        add(list1);
        add(list2);
        add(label1 = new Label("team1:                                              "));
        add(label2 = new Label("team2:                                              "));
        //List에서 익명클래스로 이벤트 연결
        list1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String str = list1.getSelectedItem();
                label1.setText("team1: " + str);
            }
        });
        list2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String str[] = list2.getSelectedItems();
                String str1 = "team2: ";
                for (int i = 0; i < str.length; i++) {
                    str1 += str[i] + " ";
                }
                label2.setText(str1);
            }
        });

        validate();
    }

    public static void main(String[] args) {
        new ListEx1();
    }
}
