package ch04;

public class Ex9 {
    public static void main(String[] args) {
        int sum = 0;
        int i =0;
        while(i<11){
            sum +=i;
            i++;
        }
        System.out.println(sum);

        while(true){
            System.out.println("무한반복");
            if(true){
                break;
            }

            do{
                System.out.println("무조건 한번은 실행");
            }while(false);
        }
    }
}
