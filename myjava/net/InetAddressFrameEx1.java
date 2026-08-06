package net;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class InetAddressFrameEx1 extends MFrame 
implements ActionListener{
	
	TextField tf;
	TextArea ta;
	Button lookup;
	InetAddress intAddr;
	
	public InetAddressFrameEx1() {
		setTitle("InetAddress Example");
		Panel p = new Panel();
		p.setLayout(new BorderLayout());
		p.add(new Label("호스트이름"),BorderLayout.NORTH);
		p.add(tf = new TextField("",40));
		p.add(lookup = new Button("호스트 정보 얻기"), BorderLayout.SOUTH);
		tf.addActionListener(this);
		lookup.addActionListener(this);
		add(p,BorderLayout.NORTH);
		ta = new TextArea("인터넷주소\n");
		ta.setFont(new Font("Dialog",Font.BOLD,15));
		ta.setForeground(Color.BLUE);
		ta.setEditable(false);
		add(ta);
		validate();
	}
	
	
	public void actionPerformed(ActionEvent e) {
        try {
            Object ch = e.getSource();
            InetAddress add = InetAddress.getLocalHost();

            if(ch ==tf ||ch == lookup){
                if(tf.getText().equals("cls")){
                    ta.setText("");
                    tf.setText("");
                    tf.requestFocus();
                    return;
                }

                try{
                    add = InetAddress.getByName(tf.getText());
                    ta.append(add.getHostName() + "\n");
                    ta.append(add.getHostAddress() + "\n");
                }catch (UnknownHostException uhe){
                    ta.append("[" + tf.getText() + "]" + "\n");
                    ta.append("해당하는 호스트가 없습니다.\n");
                }finally {
                    tf.setText("");
                    tf.requestFocus();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
	
	public static void main(String[] args) {
		new InetAddressFrameEx1();
	}
}




























