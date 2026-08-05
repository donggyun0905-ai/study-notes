package ch14;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

public class BufferedWriteEx1 {
    public static void main(String[] args) {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String str = "오늘은 정말 즐겁고 행복한 수요일입니다.";
        try{
            bw.write(str);
            bw.newLine();
            bw.write(str);
            bw.flush();
            bw.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
