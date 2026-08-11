package net.kdg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class TalkClient extends MFrame implements ActionListener,Runnable {

    Button saveBtn, sendBtn;
    TextField sendTf;
    TextArea ta;
    Panel p1, p2;
    BufferedReader in;
    PrintWriter out;
    String id, title = "Talk 1.0";

    public TalkClient(BufferedReader in, PrintWriter out, String id) {
        super(450,500);
        this.in = in;
        this.out = out;
        this.id = id;
        setTitle(title + " - " + id + "님 반갑습니다.");

        p1 = new Panel();
        p1.setBackground(new Color(64, 188, 193));
        p1.add(saveBtn = new Button("SAVE"));

        p2 = new Panel();
        p2.setBackground(new Color(64,188,193));
        p2.add(new Label("CHAT",Label.CENTER));
        p2.add(sendTf = new TextField("",25));
        p2.add(sendBtn = new Button("SEND"));

        sendTf.addActionListener(this);
        saveBtn.addActionListener(this);
        sendBtn.addActionListener(this);

        add(p1, BorderLayout.NORTH);
        add(ta = new TextArea());
        ta.setEditable(false);
        add(p2, BorderLayout.SOUTH);

        new Thread(this).start();
        validate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if(obj == saveBtn){
            String content = ta.getText();
            long fileName = System.currentTimeMillis();
            try{
                FileWriter fw = new FileWriter("net/kdg/" + fileName + ".txt");
                fw.write(content);
                fw.close();
                ta.setText("");
                new DialogBox(this,"대화내용을 저장하였습니다.","알림");
            } catch (Exception e2){
                e2.printStackTrace();
            }
        }else if (obj == sendTf || obj == sendBtn){
            String str = sendTf.getText();
            if(filterMgr(str)){
                new DialogBox(this,"입력하신 글자는 금지어입니다.","경고");
                return;
            }
            sendMessage(str);
            sendTf.setText("");
            sendTf.requestFocus();
        }
    }

    public void sendMessage(String msg){out.println(msg);}

    @Override
    public void run(){
        try{
            while(true){
                ta.append(in.readLine() + "\n");
                sendTf.requestFocus();
            }
        } catch (Exception e){
            e.printStackTrace();
            System.err.println("Error");
            System.exit(1);
        }
    }

    boolean filterMgr(String msg){
        String str[] = {"개새끼", "병신", "씨발", "엿먹어", "미친놈", "미친년"};

        String cleanMsg = msg.replaceAll("[^가-힣a-zA-Z0-9]", "");
        for (String s : str) {
            if (cleanMsg.contains(s)) {
                return true;
            }
        }
        return false;
    }

    class MDialog extends Dialog implements ActionListener{

        Button ok;
        TalkClient tc;

        public MDialog(TalkClient tc, String title, String msg){
            super(tc,title,true);
            this.tc = tc;
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });
            setLayout(new GridLayout(2,1));
            Label label = new Label(msg, Label.CENTER);
            add(label);
            add(ok = new Button("확인"));
            ok.addActionListener(this);
            layset();
            setVisible(true);
            validate();
        }

        public void layset() {
            int x = tc.getX();
            int y = tc.getY();
            int w = tc.getWidth();
            int h = tc.getHeight();
            int w1 = 150;
            int h1 = 100;
            setBounds(x + w / 2 - w1 / 2, y + h / 2 - h1 / 2, 200, 100);
        }

        public void actionPerformed(ActionEvent e) {
            sendTf.setText("");
            dispose();
        }
    }
}
