package ch14;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class FileCopyEx1 {
    public static void main(String[] args) {
        try{
            Scanner sc = new Scanner(System.in);
            System.out.print("원본파일: ");
            String oriFile = sc.nextLine();
            System.out.print("복사파일: ");
            String copyFile = sc.nextLine();
            FileReader fr = new FileReader("ch14/" + oriFile);
            FileWriter fw = new FileWriter("ch14/" + copyFile);//파일생성
            int a;
            while((a=fr.read())!=-1){
                fw.write(a);
            }
            fw.flush();
            fw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
