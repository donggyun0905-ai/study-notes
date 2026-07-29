package ch10;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class ExceptionEx5 {
    public static void main(String[] args) {
        try {
            FileReader fr = myRead("aa.txt");
        } catch (FileNotFoundException e) {
            System.err.println("지정한 파일이 없습니다.");
            e.printStackTrace();
        }
    }

    //throws: 호출한 메소드에 예외객체를 던지는 기능의 예약어
    public static FileReader myRead(String name) throws FileNotFoundException {
        FileReader fr = new FileReader(name);
        return fr;
    }
}
