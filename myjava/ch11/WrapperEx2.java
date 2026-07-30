package ch11;

public class WrapperEx2 {
    public static void main(String[] args) {
        //Wrapper Class 8개
        Byte b;
        Short s;
        Integer i;
        Long l;
        Float f;
        Double d;
        Character c;
        Boolean bl;

        char c1 = 'J';
        if (Character.isLetter(c1))
            System.out.println(c1 + "문자입니다.");
        char c2 = 'a';
        if (Character.isLowerCase(c2))
            System.out.println(c2 + "소문자입니다.");
        char c3 = '2';
        if (Character.isDigit(c3))
            System.out.println(c3 + "숫자입니다.");
        char c4 = ' ';
        if (Character.isWhitespace(c4))
            System.out.println("***" + c4 + "***" + "공백문자입니다.");
    }
}
