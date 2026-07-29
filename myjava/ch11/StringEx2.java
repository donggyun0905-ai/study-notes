package ch11;

public class StringEx2 {
    public static void main(String[] args) {
        String str = "Java Programming";
        System.out.println(str);
        System.out.println("len: " + str.length());

        //지정한 문자 및 문자열의 위치값?
        int idx1 = str.indexOf('a');//처음부터 검색
        System.out.println("idx1: " + idx1);
        int idx2 = str.indexOf('x');
        System.out.println("idx2: " + idx2);
        int idx3 = str.indexOf("va");
        System.out.println("idx3: " + idx3);

        //'a'를 'q'로 변환 출력
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if(str.charAt(i)=='a'){
                System.out.print('q');
            }else{
                System.out.print(str.charAt(i));
            }
        }
        System.out.println();
        String str2 = str.replace('a','q');
        System.out.println("str2: " + str2);

        //문자열을 반대로 출력
        for(int j=len-1;j>=0;j--){
            System.out.print(str.charAt(j));
        }
        //Stirng 약점을 보완하기 위한 클래스 StringBuffer
        StringBuffer sb = new StringBuffer(str);
        System.out.println(sb.reverse());
        System.out.println("---------------------------");

        String str3 =  "Java&JSP&Flutter&Iot&Spring";
        String str4[] = str3.split("&");
        for (int i = 0; i < str4.length; i++) {
            System.out.println(str4[i]);
        }//for

        String str5 = "       JSPStudy             ";
        System.out.println("*********" + str5.trim() + "***********");
        //정수 -> 문자열
        int idx4 = 45;
        String str6 = String.valueOf(idx4);//FM방식
        String str7 = idx4+"";//idx4가 문자열로 변환
        System.out.println(str6+str7);//4545
        //문자열 -> 정수
        System.out.println(Integer.parseInt("22")+Integer.parseInt("23"));
    }
}
