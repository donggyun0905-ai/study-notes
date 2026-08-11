package net.kdg;

import net.kdg.TalkClient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TalkAWT extends MFrame implements ActionListener {

    TextField idTf, pwTf;
    Label logo, idl, pwl, msgl;
    Button logBtn;
    Socket sock;
    BufferedReader in;
    PrintWriter out;
    int port = 8005;
    String id, ip = "127.0.0.1", title = "Talk 1.0";
    String label[] = {"ID와 PWD를 입력하세요.",
            "ID와 PWD를 확인하세요."};

    public TalkAWT() {
        super(450,500,new Color(64, 188, 193));
        setLayout(null);
        setTitle(title);
        logo = new Label(title);
        logo.setFont(new Font("Dialog", Font.BOLD, 50));

        idl = new Label("ID");
        pwl = new Label("PWD");
        idTf = new TextField("aaa");
        pwTf = new TextField("1234");
        logBtn = new Button("로그인");
        msgl = new Label(label[0]);

        logo.setBounds(120, 90, 250, 100);
        idl.setBounds(150, 240, 50, 20);
        idTf.setBounds(200, 240, 100, 20);
        pwl.setBounds(150, 270, 50, 20);
        pwTf.setBounds(200, 270, 100, 20);
        logBtn.setBounds(150, 300, 150, 40);
        msgl.setBounds(150, 360, 150, 40);
        logBtn.addActionListener(this);

        add(logo);
        add(idl);
        add(idTf);
        add(pwl);
        add(pwTf);
        add(logBtn);
        add(msgl);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        try {
            if (obj == logBtn) {
                if (sock == null) {
                    sock = new Socket(ip, port);
                    in = new BufferedReader(new InputStreamReader(
                            sock.getInputStream()));
                    out = new PrintWriter(sock.getOutputStream(), true);
                }
                out.println(idTf.getText() + ";"+ pwTf.getText());
                String data = in.readLine();
                if (data.equals("F")) {
                    msgl.setForeground(Color.red);
                    msgl.setText(label[1]);
                } else if (data.equals("T")) {
                    id = idTf.getText();
                    String name = in.readLine();
                    dispose();
                    new TalkClient(in, out, name);
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new TalkAWT();
    }
}
