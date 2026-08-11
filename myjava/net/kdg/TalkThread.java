package net.kdg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TalkThread extends Thread{

    String id;
    String name;
    BufferedReader in;
    PrintWriter out;
    Socket sock;

    public TalkThread(Socket sock) {
        try{
            this.sock = sock;
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            out = new PrintWriter(sock.getOutputStream(),true);
            System.out.println(sock + "Connected.......");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try{
            while(true){
                String line = in.readLine();
                if(line == null) break;
                if(id == null){
                    int idx = line.indexOf(";");
                    String uid = line.substring(0, idx);
                    String pwd = line.substring(idx + 1);
                    if(TalkServer.mgr.loginChk(uid, pwd)){
                        id = uid;
                        name = TalkServer.mgr.nameChk(uid);
                        sendMessage("T");
                        sendMessage(name);
                    } else {
                        sendMessage("F");
                    }
                } else {
                    TalkServer.sendAllMessage("[" + name + "]" + line);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            TalkServer.removeClient(this);
            System.err.println(sock + "Disconnected.......");
        }
    }

    public void sendMessage(String msg){out.println(msg);}
}
