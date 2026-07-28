package ch09;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MFrame extends Frame{

    MFrame(){
        this(300,300,new Color(220,220,220));
    }

    MFrame(int w, int h){
        this(w,h,new Color(220,220,220));
    }

    MFrame(Color c){
        this(300,300,c);
    }

    MFrame(int w, int h, Color c) {
        setTitle("제목");
        setSize(w, h);
        setBackground(c);
        setResizable(true);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        validate();
    }

    public static void main(String[] args) {
        //MFrame mf = new MFrame(500,200, Color.ORANGE);
        //MFrame mf2 = new MFrame(Color.PINK);
        MFrame mf = new MFrame(Color.GREEN);
    }
}
