package ch14;

import java.io.FileWriter;

public class FileWriterEx1 {
    public static void main(String[] args) {
        String str = "우리들은 현실에서 벗어나 자유를 꿈꾸지만,\n"
                + "막상 거기엔 그이상도 그이하도 아무것도 없는 것 같다.\n"
                + "오히려 자유를 누리게 되면 두려움을 느끼고 현실에 얽매이고\n"
                + "싶어한다.";
        try{
            //생성자 호출로 파일이 생성됩니다.(일반 텍스트만.)
            FileWriter fw = new FileWriter("ch14/bbb.txt");
            fw.write(str);
            fw.flush();
            fw.close();
            System.out.println("End~~~");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
