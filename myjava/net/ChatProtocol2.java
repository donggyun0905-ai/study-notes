package net;

public class ChatProtocol2 {

    //(C->S) ID:aaa
    //(S->C)CHATLIST;
    public static final String ID = "ID";

    //(C->S) CHAT : 받는 아이디; 메시지 ex)CHAT:bbb;밥먹자
    //(S->C) CHAT : 보내는 아이디; 메세지 ex) CHAT:aaa; 밥먹자
    public static final String CHAT = "CHAT";

    //(C->S) CHATALL:메세지 ex)CHATALL:밥먹읍시다
    //(S->C) CHATALL:[보낸아이디] 메세지 ex) CHATALL: [aaa] 밥먹읍시다.
    public static final String CHATALL = "CHATALL";

    //(S->C) CHATLIST:aaa;bbb;ccc;홍길동;
    public static final String CHATLIST = "CHATLIST";

    //(C->S) MESSAGE:받는아이디; 쪽지내용 ex) MESSAGE:bbb;우리 사귈까?
    //(S->C) MESSAGE:보내는아이디; 쪽지내용 ex) MESSAGE:aaa;우리 사귈까?
    public static final String MESSAGE = "MESSAGE";

    public static final String MODE1 = ":";
    public static final String MODE2 = ";";
}
