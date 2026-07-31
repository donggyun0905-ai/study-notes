package awt;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ChoiceEx3 extends MFrame implements ItemListener {

    Choice select,idol;
    String gubun[] = {"남자 연예인","여자 연예인"};
    String ms[] = {"현 빈","원 빈","이민호","김수현","김우빈","이종석"};
    String fs[] = {"고아라","이연희","이하늬","문채원","수 지","김연아"};

    public ChoiceEx3() {
        super(300,300,new Color(200,200,200));
        setTitle("아이돌");
        select = new Choice();
        idol = new Choice();
        setting(select, gubun);
        setting(idol, ms);
    }

    public void setting(Choice idol, String sidol[]){
        for (int i = 0; i < sidol.length; i++) {
            idol.add(sidol[i]);
        }
        idol.addItemListener(this);
        add(idol);
    }

    public void result(Graphics g){
        g.setColor(Color.BLUE);
        String str = "연예인 성별 : " + select.getSelectedItem();
        g.drawString(str,30,120);

        g.setColor(Color.RED);
        String str2 = "선택 연예인 : " + idol.getSelectedItem();
        g.drawString(str2,30,150);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getSource() == select){
            idol.removeAll();
            if(select.getSelectedItem().equals("남자 연예인")){
                for(String s : ms) idol.add(s);
            }else{
                for(String s : fs) idol.add(s);
            }
        }
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(select == null || idol == null)
            return;
        result(g);
    }

    public static void main(String[] args) {new ChoiceEx3();}

}
