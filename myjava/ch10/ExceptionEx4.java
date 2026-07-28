package ch10;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ExceptionEx4 {
    public static void main(String[] args) {

        FileReader fr = null;
        try {
            fr = new FileReader("ch10/test.txt");
            int a;
            while((a=fr.read())!=-1){
                System.out.println((char)a);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /*
        * 1. FileReader fr = new FileReader("ch10/test.txt"); 이 한 줄만 선택 → Ctrl+Alt+T → try/catch
→ FileNotFoundException 하나만 잡는 try/catch가 만들어짐
2. 그 다음 int a; while(...)... 블록을 그 try 블록 안으로 옮겨 넣기 (커서로 끌어서 넣거나 잘라내서 붙여넣기)
3. 그러면 fr.read() 줄에 빨간 밑줄(unhandled IOException)이 뜰 거예요 — 이때 그 줄에 커서 놓고 Alt+Enter → "Add catch clause" 선택
→ 이번엔 기존 catch를 안 건드리고 새 catch를 추가로 만들어줌
        * */
    }
}
