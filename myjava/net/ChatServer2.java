package net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import net.ChatServer1.Client1;

public class ChatServer2 {
	
	public static final int PORT = 8002;
	Vector<Client2> vc;//접속한 Client 객체를 저장하는 벡터
	ServerSocket server;
	
	public ChatServer2() {
		try {
			server = new ServerSocket(PORT);
			vc = new Vector<Client2>();
		} catch (Exception e) {
			System.err.println("Error in Server");
			System.exit(1);//비정상적인 종료
		}
		System.out.println("*****************************");
		System.out.println("****ChatServer v2.0 Start****");
		System.out.println("*****************************");
		try {
			while(true) {
				Socket sock = server.accept();
				Client2 ct = new Client2(sock);
				ct.start();
				vc.add(ct);
			}
		} catch (Exception e) {
			System.err.println("Error in Socket");
		}
	}
	
	//전체 메세지 보내는 기능
	public void sendAllMessage(String msg) {
		for (Client2 ct : vc) {
			ct.sendMessage(msg);
		}
	}
	
	//연결이 끊어진 Client Vector에서 제거
	public void removeClient(Client2 ct) {
		vc.remove(ct);
	}

    //접속한 클라이언트 모든 id
    //Vector -> Client2 -> id 필드 -> aaa;bbb;ccc;
    public String getIdList(){
        String list = "";
        for (Client2 ct : vc){
            list += ct.id + ChatProtocol2.MODE2;
        }
        return list;
    }

    //매개변수로 들어온 id로 CHAT,MESSAGE 기능을 위해 Client2를 찾음
    public Client2 findClient(String id){
        synchronized (vc){ //다른 쓰레드가 add/remove로 부터 보호
            for(Client2 ct:vc){
                if(ct.id.equals(id)){
                    return ct;
                }
            }
        }
        return null;
    }

	class Client2 extends Thread{

        String id;
		Socket sock;
		BufferedReader in;
		PrintWriter out;
		
		public Client2(Socket sock) {
			try {
				this.sock = sock;
				in = new BufferedReader(
						new InputStreamReader(sock.getInputStream()));
				out = new PrintWriter(sock.getOutputStream(), true);
				System.out.println(sock + " Connected.......");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		@Override
		public void run() {
			try{
			    sendMessage("사용할 아이디를 입력하세요.");
                while(true){
                    String line = in.readLine();
                    if(line==null)break;
                    else routine(line);
                }
			} catch (Exception e){
			    removeClient(this);
                System.err.println(sock + "Disconnected.......");
                //ChatList 새로고침
                String str = ChatProtocol2.CHATLIST;
                str += ChatProtocol2.MODE1+getIdList();
                sendAllMessage(str);//접속된 모든 사용자에게 전송
                sendAllMessage(ChatProtocol2.CHATALL+ChatProtocol2.MODE1+id+"님이 나갔습니다.");
			}
		}

        //Client로 전달된 메시지 분석
        public void routine(String line){
            //CHATALL:오늘은 즐거운 금요일입니다.
            /*int idx = line.indexOf(ChatProtocol2.MODE1);
            String cmd = line.substring(0,idx);//CHATALL
            String data = line.substring(idx+1);//오늘은 즐거운 금요일입니다*/
            String[] result = parseRoutine(line,ChatProtocol2.MODE1);
            if(result[0].equals(ChatProtocol2.ID)){
                id = result[1];
                //새로운 접속자가 추가되면서 ChatList 전송
                sendAllMessage(ChatProtocol2.CHATLIST+ChatProtocol2.MODE1+getIdList());
                //새로운 접속자의 welcome 메세지 전송
                sendAllMessage(ChatProtocol2.CHATALL + ChatProtocol2.MODE1 + "[" + id + "]님이 입장하였습니다.");
            }else if (result[0].equals(ChatProtocol2.CHATALL)){
                sendAllMessage(ChatProtocol2.CHATALL+ChatProtocol2.MODE1 + "[" + id + "]" + result[1]);
            }else if (result[0].equals(ChatProtocol2.CHAT)){
                //CHAT:bbb; 오늘 머해?
                result = parseRoutine(result[1],ChatProtocol2.MODE2);
                //result[0]: bbb,result[1]: 오늘 머해?
                Client2 ct =  findClient(result[0]);
                if(ct!=null){
                    //bbb에 보냄
                    ct.sendMessage(ChatProtocol2.CHAT + ChatProtocol2.MODE1 + "[" + id +"(S)]" + result[1]);
                    //자신(aaa)에게 보냄
                    this.sendMessage(ChatProtocol2.CHAT + ChatProtocol2.MODE1 + "[" + id +"(S)]" + result[1]);
                }else{
                    this.sendMessage(ChatProtocol2.CHAT + ChatProtocol2.MODE1 + "[" + result[0] /*bbb*/ +"]님이 현재 접속자가 아닙니다.");
                }
            }else if (result[0].equals(ChatProtocol2.MESSAGE)){
                //MESSAGE:ccc; 토요일 한잔 어때?
                result = parseRoutine(result[1],ChatProtocol2.MODE2);
                //result[0]: ccc,result[1]: 오늘 머해?
                Client2 ct = findClient(result[0]);
                if(ct!=null){
                    //ccc에게 메세지 보냄
                    ct.sendMessage(ChatProtocol2.MESSAGE+ChatProtocol2.MODE1+id/*aaa*/+ChatProtocol2.MODE2+result[1]);
                }else{
                    //ccc가 현재 접속자가 아닌 경우 -> aaa에게 메세지 보냄
                    sendMessage(ChatProtocol2.CHAT + ChatProtocol2.MODE1 + "[" + result[0] +"]님이 현재 접속자가 아닙니다.");

                }
            }
        }

		//연결된 Client에 메세지 보내는 기능
		public void sendMessage(String msg) {
			out.println(msg);
		}

        //매개변수를 구분자를 기준으로 문자배열 리턴
        public String[] parseRoutine(String line, String mode){
            int idx = line.indexOf(mode);
            String data1 = line.substring(0,idx);//CHATALL
            String data2 = line.substring(idx+1);
            return new String[] {data1,data2};
        }
	}
	
	public static void main(String[] args) {
		new ChatServer2();
	}
}





