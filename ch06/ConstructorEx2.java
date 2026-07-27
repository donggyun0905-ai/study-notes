package ch06;

import java.awt.*;
import java.awt.Frame;
import java.awt.Dialog;

class Constructor2{
    /*JVM은 생성자가 하나라도 선언이 되어 있으면 default 생성자를 제공 안함
    * SUN 제공되는 클래스 중에 많이는 않지만 디폴트 생성자 없는 클래스 있음
    * => 결론적으로 반드시 생성자 한개는 필요(생성자만의 기능)*/

    Constructor2(int a){

    }
}

public class ConstructorEx2 {
    public static void main(String[] args) {
        //매개변수 있는 생성자 선언이 되면 디폴트 생성자
        Constructor2 c1 = new Constructor2(22);
        //흔하지는 않지만 sun에서 제공되는 클래스 중에 디폴트 생성자 없는 클래스
        Dialog d = new Dialog(new Frame());

    }
}
