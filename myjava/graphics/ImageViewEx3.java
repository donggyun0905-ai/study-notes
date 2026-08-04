package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImageViewEx3 extends JFrame implements ActionListener {

    static final int SHOW_NORM = 0;//정상그림
    static final int SHOW_PART = 1;//일부그림
    static final int SHOW_SCALE = 2;//축소그림
    static final int SHOW_HORIZONTAL = 3;//좌우뒤집기
    static final int SHOW_VERTICAL = 4;//상하뒤집기
    static final int SHOW_ALL = 5;//전부뒤집기

    Image img;
    JButton btn[] = new JButton[6];
    String str[] = {"정상그림","일부그림","축소그림","좌우뒤집기","상하뒤집기","전부뒤집기"};
    int idx = 0;
    String name;
    //이미지 크기
    int iw,ih;

    public ImageViewEx3(String name) {
        setSize(600,400);
        //완전한 종료 설정
        this.name = name;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel p = new JPanel();
        for (int i = 0; i < btn.length; i++) {
            p.add(btn[i]=new JButton(str[i]));
            btn[i].addActionListener(this);
        }
        img = Toolkit.getDefaultToolkit().getImage("graphics/"+name);
        add(p, BorderLayout.SOUTH);

        setResizable(false);
        setVisible(true);
        validate();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        setTitle(name + " - " + str[idx]);
        iw = img.getWidth(this);
        ih = img.getHeight(this);
        switch (idx){
            case SHOW_NORM :
                g.drawImage(img,0,0,this);
                break;
            case SHOW_PART:
                g.drawImage(img,0,0,200,200/*프레임 좌표*/
                        ,220,20,420,220/*이미지 좌표*/,this);
                break;
            case SHOW_SCALE:
                g.drawImage(img,0,0,iw /2,ih/2,/*이미지*/
                        0,0,iw,ih,this);
                break;
            case SHOW_HORIZONTAL:
                g.drawImage(img,iw,0,0,ih,/*이미지*/
                        0,0,iw,ih,this);
                break;
            case SHOW_VERTICAL:
                g.drawImage(img,0,ih,iw,0,/*이미지*/
                        0,0,iw,ih,this);
                break;
            case SHOW_ALL:
                g.drawImage(img,iw,ih,0,0,/*이미지*/
                        0,0,iw,ih,this);
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        for (int i = 0; i < btn.length; i++) {
            if(btn[i] == obj){
                idx = i;
                break;
            }
        }
        repaint();
    }

    public static void main(String[] args) {
        new ImageViewEx3("aaa.png");
    }
}
