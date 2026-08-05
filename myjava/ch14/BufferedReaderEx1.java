package ch14;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BufferedReaderEx1 {
    public static void main(String[] args) {

        /*InputStream is = System.in;
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);*/

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = "";
        try{
            while(true){
                str = br.readLine(); //한줄씩 읽어옴.
                System.out.println("출력: " + str);
                if(str.equals("exit"))
                    break;
            }
            br.close();
            System.out.println("종료~");
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
