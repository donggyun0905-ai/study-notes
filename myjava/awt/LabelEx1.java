package awt;

import java.awt.*;

public class LabelEx1 extends MFrame{

    Label label[];
    int pos[] = {Label.LEFT, Label.CENTER, Label.RIGHT, Label.LEFT};

    //생성자는 객체가 생성이 될때 한번만 호출되는 특별한 메소드이기 때문에 기본적인 초기화가 설정이 필요
    public LabelEx1() {
        //3개 이상이면 배열로 선언함.
        //Label 객체를 담을 수 있는 4개의 칸이 만들어짐. <- 절대 객체가 만들어진게 아님.
        label = new Label[4];
        //label[0].setBackground(Color.black); //NullPointerException
        String str = "오늘은 행복한 금요일";
        for (int i = 0; i < label.length; i++) {
            //실제 Label 객체 생성
            label[i] = new Label(str,pos[i]);
            label[i].setBackground(MColor.rColor());
            //현재 컨테이너 add <-Frame
            add(label[i]);
        }
        validate();
    }

    public static void main(String[] args) {
        new LabelEx1();
    }
}
