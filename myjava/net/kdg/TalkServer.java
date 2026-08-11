package net.kdg;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class TalkServer {

    public static final int PORT = 8005;
    static Vector<TalkThread> vc = new Vector<TalkThread>();
    ServerSocket server;
    static TalkMgr mgr;

    public TalkServer(){
        try{
            server = new ServerSocket(PORT);
            mgr = new TalkMgr();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Error in Server");
            System.exit(1);
        }

        System.out.println("****************************");
        System.out.println("******TalkServer Start******");
        System.out.println("****************************");

        try{
            while(true){
                Socket sock = server.accept();
                TalkThread tt = new TalkThread(sock);
                tt.start();
                vc.add(tt);
            }
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Error in Socket");
        }
    }

    //전체 메세지 보내는 기능
    public static void sendAllMessage(String msg) {
        for (int i = 0; i < vc.size(); i++) {
            TalkThread ct = vc.get(i);
            ct.sendMessage(msg);
        }
    }

    //연결이 끊어진 Client Vector에서 제거
    public static void removeClient(TalkThread ct) {
        vc.remove(ct);
    }

    public static void main(String[] args) {
        new TalkServer();
    }
}
