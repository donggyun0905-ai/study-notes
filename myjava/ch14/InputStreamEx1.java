package ch14;

import java.io.InputStream;

public class InputStreamEx1 {

    public static void main(String[] args) {
        InputStream is = System.in; //키보드
        try {
            while(true){
                int a = is.read();//내부적인 쓰레드 : 입력대기 상태
                if(a == -1/*ctrl + z*/) break;
                System.out.print((char)a);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
