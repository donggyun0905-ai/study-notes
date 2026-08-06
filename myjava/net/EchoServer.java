package net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    public static final int PORT = 8000;

    public EchoServer() {
        try {
            int cnt = 0; // 접속한 Client 개수
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("EchoServer Start---------------------------");
            while (true) {
                Socket sock = server.accept(); // Client가 접속 할때까지 대기상태
                EchoThread et = new EchoThread(sock);
                et.start();
                cnt++;
                System.out.println("Client " + cnt + "Socket");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Client 요청에 각자 대응하기 위한 Thread 상속 내부 클래스
    class EchoThread extends Thread{

        // C/S 연결 스트림을 가지고 있는 객체
        Socket sock;
        BufferedReader in;
        PrintWriter out;

        public EchoThread(Socket sock) {
            try {
                this.sock = sock;
                in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                out = new PrintWriter(sock.getOutputStream(), true); // true는 flush auto
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                // Client가 접속 후에 최초로 받는 메세지
                out.println("Hello Enter BYE to exit");
                while (true) {
                    String line = in.readLine(); // Client로부터 메세지 들어오기 전까지 대기상태
                    if (line == null) break;
                    else{
                        out.println("Echo: " + line); // Client에서 넘어온 문자열을 Echo 추가해서 다시 보냄
                        if (line.equalsIgnoreCase("bye")) {
                            break;
                        }
                    }
                }
                in.close();
                out.close();
                sock.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        new EchoServer();
    }
}
