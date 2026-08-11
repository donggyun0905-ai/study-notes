package net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ChatServer3 {

	public static final int PORT = 8003;
	Vector<Client3> vc;//접속한 Client 객체를 저장하는 벡터
	ServerSocket server;
	ChatMgr3_1 mgr;

	public ChatServer3() {
		try {
			server = new ServerSocket(PORT);
			vc = new Vector<Client3>();
			mgr = new ChatMgr3_1();
		} catch (Exception e) {
			System.err.println("Error in Server");
			System.exit(1);//비정상적인 종료
		}
		System.out.println("*****************************");
		System.out.println("****ChatServer v3.0 Start****");
		System.out.println("*****************************");
		try {
			while(true) {
				Socket sock = server.accept();
				Client3 ct = new Client3(sock);
				ct.start();
				vc.add(ct);
			}
		} catch (Exception e) {
			System.err.println("Error in Socket");
		}
	}
	
	//전체 메세지 보내는 기능
	public void sendAllMessage(String msg) {
		for (Client3 ct : vc) {
			ct.sendMessage(msg);
		}
	}
	
	//연결이 끊어진 Client Vector에서 제거
	public void removeClient(Client3 ct) {
		vc.remove(ct);
	}

    //접속한 클라이언트 모든 id
    //Vector -> Client2 -> id 필드 -> aaa;bbb;ccc;
    public String getIdList(){
        String list = "";
        for (Client3 ct : vc){
            list += ct.id + ChatProtocol3.MODE2;
        }
        return list;
    }

    //매개변수로 들어온 id로 CHAT,MESSAGE 기능을 위해 Client2를 찾음
    public Client3 findClient(String id){
        Client3 ct = null;
		for(Client3 ct2:vc){
			if(id.equals(ct2.id)){
				ct = ct2;
				break;
			}
		}
		return ct;
    }

	class Client3 extends Thread{

        String id;
		Socket sock;
		BufferedReader in;
		PrintWriter out;
		
		public Client3(Socket sock) {
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
			    //sendMessage("사용할 아이디를 입력하세요."); <---제거
                while(true){
                    String line = in.readLine();
                    if(line==null)break;
                    else routine(line);
                }
			} catch (Exception e){
			    removeClient(this);
                System.err.println(sock + "Disconnected.......");
                //ChatList 새로고침
                String str = ChatProtocol3.CHATLIST;
                str += ChatProtocol3.MODE1+getIdList();
                sendAllMessage(str);//접속된 모든 사용자에게 전송
                sendAllMessage(ChatProtocol3.CHATALL+ChatProtocol3.MODE1+id+"님이 나갔습니다.");
			}
		}

        //Client로 전달된 메시지 분석
        public void routine(String line){
            //CHATALL:오늘은 즐거운 금요일입니다.
            /*int idx = line.indexOf(ChatProtocol3.MODE1);
            String cmd = line.substring(0,idx);//CHATALL
            String data = line.substring(idx+1);//오늘은 즐거운 금요일입니다*/
            String[] result = parseRoutine(line,ChatProtocol3.MODE1);
            if(result[0].equals(ChatProtocol3.ID)) {
				//ID :aaa;1234
				result = parseRoutine(result[1], ChatProtocol3.MODE2);
				//result[0]:aaa, result[1] : 1234
				//로그인 성공 : T,로그인 실패 : F, 이중 접속 : C
				if (mgr.loginChk(result[0], result[1])) {
					//이중 접속은 Vector에서 동일한 id로 저장한 Client3가 있는지 검색
					Client3 ct = findClient(result[0]/*aaa*/);
					if (ct != null && ct.id.equals(result[0])) {
						//이중 접속
						sendMessage(ChatProtocol3.ID + ChatProtocol3.MODE1 + "C");
					} else {
						//완전한 로그인 성공
						id = result[0];
						sendMessage(ChatProtocol3.ID + ChatProtocol3.MODE1 + "T");
						//Chat list : 접속된 모든 사용자에게 갱신된 목록 전송
						sendAllMessage(ChatProtocol3.CHATLIST + ChatProtocol3.MODE1 + getIdList());
						//welcome 메세지 : 접속된 모든 사용자에게 전송
						sendAllMessage(ChatProtocol3.CHATALL + ChatProtocol3.MODE1 + "[" + id + "]님이 입장하였습니다.");
					}
				} else {
					//로그인 실패
					sendMessage(ChatProtocol3.ID + ChatProtocol3.MODE1 + "F");
				}
			}else if (result[0].equals(ChatProtocol3.CHATALL)) {
				sendAllMessage(ChatProtocol3.CHATALL + ChatProtocol3.MODE1 + "[" + id + "]" + result[1]);
            }else if (result[0].equals(ChatProtocol3.CHAT)){
                //CHAT:bbb; 오늘 머해?
                result = parseRoutine(result[1],ChatProtocol3.MODE2);
                //result[0]: bbb,result[1]: 오늘 머해?
                Client3 ct =  findClient(result[0]);
                if(ct!=null){
                    //bbb에 보냄
                    ct.sendMessage(ChatProtocol3.CHAT + ChatProtocol3.MODE1 + "[" + id +"(S)]" + result[1]);
                    //자신(aaa)에게 보냄
                    this.sendMessage(ChatProtocol3.CHAT + ChatProtocol3.MODE1 + "[" + id +"(S)]" + result[1]);
                }else{
                    this.sendMessage(ChatProtocol3.CHAT + ChatProtocol3.MODE1 + "[" + result[0] /*bbb*/ +"]님이 현재 접속자가 아닙니다.");
                }
            }else if (result[0].equals(ChatProtocol3.MESSAGE)){
				//MESSAGE:bbb;오늘 저녁을 먹자
				result = parseRoutine(result[1],ChatProtocol3.MODE2);
				//result[0]:bbb,result[1]:오늘 저녁을 먹자.
				MessageBean3 bean = new MessageBean3();
				bean.setFid(id);//aaa
				bean.setTid(result[0]);//bbb
				bean.setMsg(result[1]);//오늘 저녁을 먹자.
				mgr.insertMsg(bean);
				Client3 ct = findClient(result[0]);
				if (ct!=null){
					ct.sendMessage(ChatProtocol3.MESSAGE + ChatProtocol3.MODE1 + id + ChatProtocol3.MODE2 + result[1]);
				}
            }else if (result[0].equals(ChatProtocol3.MSGLIST)){
				//MSGLST : aaa,bbb, 밥먹자; bbb,ccc,오늘머해?
				Vector<MessageBean3> vlist = mgr.getMsgList(id);
				String str = "";
				for (MessageBean3 bean:vlist){
					str += bean.getFid() + ",";
					str += bean.getTid() + ",";
					str += bean.getMsg() + ChatProtocol3.MODE2;
				}//for
				sendMessage(ChatProtocol3.MSGLIST+ChatProtocol3.MODE1 + str);
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
		new ChatServer3();
	}
}





