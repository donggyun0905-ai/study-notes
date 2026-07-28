package ch10;

public class ExceptionEx1 {
    public static void main(String[] args) {
        try{
            int a = 10, b= 0;
            System.out.println(a+b);
            System.out.println(a-b);
            System.out.println(a*b);
            System.out.println(a/b);
            System.out.println(a%b);
        }catch (Exception e){
            System.out.println("응 아니야~");
            System.out.println(e.getMessage());
        }finally {
            System.out.println("응 맞아~");
        }
        System.out.println("끝~");

    }
}
