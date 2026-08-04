package graphics;

import java.awt.*;

public class ImageViewEx1 extends MFrame {

    Image img;

    public ImageViewEx1() {
        super(500,300);
        img = Toolkit.getDefaultToolkit().getImage("graphics/winter.jpg");

    }

    /*this: ImageObserver 타입, 이미지 로딩이 다 될때까지 기다리는 기능의 인터페이스
    * 코드에서는 paint 직접적인 호출은 불가능하고 구조적으로 repaint -> update -> paint
    *  */

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        System.out.println(111);
        g.drawImage(img,0,0,this);
    }

    public static void main(String[] args) {
        new ImageViewEx1();
    }
}
