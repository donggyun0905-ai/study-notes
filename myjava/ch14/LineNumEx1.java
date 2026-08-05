package ch14;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;

public class LineNumEx1 {
    public static void main(String[] args) {

        try{
            FileReader fr = new FileReader("ch14/LineNumEx1.java");
            BufferedReader br = new BufferedReader(fr);

            FileOutputStream fos = new FileOutputStream("ch14/LineNumEx1.txt");
            //다목적 출력스트림
            PrintStream ps = new PrintStream(fos);
            int a = 1;
            String str = "";
            while(true){
                str = br.readLine();
                if(str == null) break;
                str = a + " : " + str;
                ps.println(str);
                a++;
            }
            ps.close();
            fos.close();
            br.close();
            fr.close();
            System.out.println("End~~~~~~~");
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
