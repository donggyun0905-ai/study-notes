package ch11;

public class StringEx3 {
    public static void main(String[] args) {
        String str = "전지현이가 백화점에서 팬사인회를 연다."
                + "전지현은 5일 오후 3시 서울 소공동 롯데 백화점"
                + " 8층 이벤트홀에서... 구두 브랜드 조이제화의"
                + " 홍보를 위한 팬사인회에 참석한다.";
        int len = str.length();
        System.out.println(len);//93
        /*1번.순방향으로 공백문자의 index 번호를 출력하시오.
		5, 11,......87, : hint : indexOf을 사용한다.*/

		/*2번.역방향으로 공백문자의 index 번호를 출력하시오.
		87, 81, 78,....5,  : hint : lastIndexOf을 사용한다.*/

        /*3번.빈칸을 '_' 출력하시오. hint : charAt*/

        /*4번 첫단어 출력하기 : substring, indexOf*/

        /*5번 마지막단어 출력하기 : substring, lastIndexOf*/
        int idx1 = str.indexOf(' ');
        for (int i = 0; i < len; i++) {
            System.out.print(idx1 + ", ");
            idx1 = str.indexOf(' ',idx1 + 1);
            if(idx1 < 0)
                break;
        }
        System.out.println();

        int idx2 = str.lastIndexOf(' ');
        for (int i = 0; i < len; i++) {
            System.out.print(idx2 + ", ");
            idx2 = str.lastIndexOf(' ',idx2 - 1);
            if(idx2 < 0)
                break;
        }
        System.out.println();

        for (int i = 0; i < len; i++) {
            if(str.charAt(i) ==' '){
                System.out.print('_');
            }else{
                System.out.print(str.charAt(i));
            }
        }
        System.out.println();

        /*4번 첫단어 출력하기 : substring, indexOf*/
        String firstWord = str.substring(0, str.indexOf(' '));
        System.out.println(firstWord);

        /*5번 마지막단어 출력하기 : substring, lastIndexOf*/
        String lastWord = str.substring(str.lastIndexOf(' ') + 1);
        System.out.println(lastWord);
    }
}
