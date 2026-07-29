package ch11;

public class StringBufferEx1 {
    public static void main(String[] args) {
        //StringBuffer: String 저장소에 만들어지고 변경되지 않는 문자열의 단점을 보완.
        //String에서 제공되는 메소드는 90% 제공
        StringBuffer sb = new StringBuffer();
        sb.append("Java");
        System.out.println(sb.hashCode());
        sb.append(" Programming");
        System.out.println(sb.hashCode());
        sb.replace(0,4,"Flutter");
        System.out.println(sb);
        sb.deleteCharAt(0);
        sb.reverse();
        System.out.println(sb);
    }
}
