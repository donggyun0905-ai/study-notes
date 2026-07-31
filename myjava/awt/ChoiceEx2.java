package awt;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ChoiceEx2 extends MFrame implements ItemListener {


    Choice air,city;
    String sair[] = {"대한항공","아시아나","에어부산","진에어"};
    String scity[] = {"서 울","대 전","대 구","부 산","제주도"};

    public ChoiceEx2() {
        super(300,300,new Color(100,200,100));
        setTitle("Chocie 예제2");
        air = new Choice();
        for (int i = 0; i < sair.length; i++) {
            air.add(sair[i]);
        }
        city = new Choice();
        for (int i = 0; i < scity.length; i++) {
            city.add(scity[i]);
        }
        //city에 아이템에 수정이 일어나면 itemStateChanged() 메소드 호출
        city.addItemListener(this);
        add(air);
        add(city);

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        //System.out.println("호출");
        repaint();//결과적으로 paint 메소드 호출
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //System.out.println("paint 호출");
        if(air == null||city==null) //air, city 객체가 생성되기 전에는 그러면 안됨.
            return;
        g.setColor(Color.BLUE);//붓에 파랑 셋팅
        String str = "항공사 선택 : " + air.getSelectedItem();
        g.drawString(str,30,120/*x,y 좌표값*/);

        g.setColor(Color.RED);
        String str2 = "도시 선택 : " + city.getSelectedItem();
        g.drawString(str2,30,150);
    }


    public static void main(String[] args) {
        new ChoiceEx2();
    }
}
